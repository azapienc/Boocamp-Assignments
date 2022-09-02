package com.bootcampzapien.assignment_1.service;

import com.bootcampzapien.assignment_1.dto.RequestDto;
import com.bootcampzapien.assignment_1.dto.ResponseDto;
import com.bootcampzapien.assignment_1.entity.EmployeeData;
import com.bootcampzapien.assignment_1.entity.Skill;
import com.bootcampzapien.assignment_1.exception.BootcampExperienceException;
import com.bootcampzapien.assignment_1.mapper.DaoMapper;
import com.bootcampzapien.assignment_1.mapper.DtoMapper;
import com.bootcampzapien.assignment_1.publisher.SampleProducer;
import com.bootcampzapien.assignment_1.repository.EmployeeDataRepository;
import com.bootcampzapien.assignment_1.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeDataRepository employeeDataRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private DaoMapper daoMapper;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private SampleProducer producer;

    /**
     * creates a new employee
     *
     * @param newRequestDtoDto
     * @return Employee mono
     */
    public Mono<ResponseDto> createEmployee(RequestDto newRequestDtoDto) {
        log.info("Creating new employee");
        return employeeDataRepository.existsById(newRequestDtoDto.getEmp_id())
                .flatMap(exists -> {
                    return exists ? skillRepository.existsById(newRequestDtoDto.getEmp_id()) : Mono.just(false);
                })
                .flatMap(exists -> {
                    String message = exists ? "Already Exists" : "Created";
                    return Mono.zip(
                                    exists ? employeeDataRepository.findById(newRequestDtoDto.getEmp_id())
                                            : employeeDataRepository.save(this.daoMapper.requestToEmployeeData(newRequestDtoDto)),
                                    exists ? skillRepository.findById(newRequestDtoDto.getEmp_id())
                                            : skillRepository.save(this.daoMapper.requestToSkill(newRequestDtoDto)),
                                    mergeEmployeeDataAndSkill())
                            .map(requestDto -> {
                                return dtoMapper.requestToResponse(requestDto, message);
                            });
                })
                .map(this::publishToKafka);
    }

    public Flux<RequestDto> findEmpSkillset(RequestDto requestDto) throws BootcampExperienceException {
        //TODO:
        if(requestDto.getJava_exp()<0){
            throw new BootcampExperienceException("Negative java experience");
        }

        return skillRepository.findAll()
                .filter(skill -> skill.getJavaExp() >= requestDto.getJava_exp())
                .flatMap(skill -> {
                    return employeeDataRepository.findById(skill.getEmpId()).flatMap(employeeData -> {
                        return Mono.zip(
                                Mono.just(employeeData),
                                Mono.just(skill),
                                mergeEmployeeDataAndSkill());
                    });
                });
    }

    private BiFunction<EmployeeData, Skill, RequestDto> mergeEmployeeDataAndSkill() {
        return (a, b) -> {
            return daoMapper.convert(a, b);
        };
    }

    private ResponseDto publishToKafka(ResponseDto message) {
        if (message.getStatus().equals("Created")) {
            try {
                producer.sendMessage(SampleProducer.TOPIC, dtoMapper.responseToRequest(message));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return message;
    }

}

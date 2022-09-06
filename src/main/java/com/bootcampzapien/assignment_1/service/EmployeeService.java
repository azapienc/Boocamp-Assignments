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
import com.bootcampzapien.assignment_1.utils.Status;
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
     * @return Employee Mono
     */
    public Mono<ResponseDto> createEmployee(RequestDto newRequestDtoDto) {
        log.info("Creating new employee");
        return employeeDataRepository.existsById(newRequestDtoDto.getEmp_id())
                .flatMap(exists -> exists ? skillRepository.existsById(newRequestDtoDto.getEmp_id()) : Mono.just(false))
                .flatMap(exists -> {
                    String message = exists ? Status.ALREADY_EXISTS.label : Status.CREATED.label;
                    return Mono.zip(
                                    exists ? employeeDataRepository.findById(newRequestDtoDto.getEmp_id())
                                            : employeeDataRepository.save(this.daoMapper.requestToEmployeeData(newRequestDtoDto)),
                                    exists ? skillRepository.findById(newRequestDtoDto.getEmp_id())
                                            : skillRepository.save(this.daoMapper.requestToSkill(newRequestDtoDto)),
                                    mergeEmployeeDataAndSkill())
                            .map(requestDto -> dtoMapper.requestToResponse(requestDto, message));
                })
                .map(this::publishToKafka);
    }

    /**
     * Look for users with matching skills
     *
     * @param requestDto
     * @return Employee Flux
     * @throws BootcampExperienceException
     */
    public Flux<RequestDto> findEmpSkillset(RequestDto requestDto) throws BootcampExperienceException {
        return getSkillSet(requestDto)
                .flatMap(skill -> employeeDataRepository
                        .findById(skill.getEmpId())
                        .flatMap(employeeData -> Mono.zip(
                                Mono.just(employeeData),
                                Mono.just(skill),
                                mergeEmployeeDataAndSkill())
                        )
                );
    }

    /**
     * Evaluate which skill is going to search for
     *
     * @param requestDto
     * @return Flux
     * @throws BootcampExperienceException
     */
    private Flux<Skill> getSkillSet(RequestDto requestDto) throws BootcampExperienceException {
        if (requestDto.getJava_exp() == null && requestDto.getSpring_exp() == null)
            throw new BootcampExperienceException("No parameter to evaluate");
        else if (requestDto.getJava_exp() != null && requestDto.getSpring_exp() != null)
            throw new BootcampExperienceException("Only one parameter is allowed");
        else if (requestDto.getJava_exp() != null)
            return skillRepository.findByJavaExpGreaterThan(requestDto.getJava_exp());
        else if (requestDto.getSpring_exp() != null)
            return skillRepository.findBySpringExpGreaterThan(requestDto.getSpring_exp());
        throw new BootcampExperienceException("Invalid parameters");
    }

    /**
     * Merge employee with its skills
     *
     * @return DTO with composed properties
     */
    private BiFunction<EmployeeData, Skill, RequestDto> mergeEmployeeDataAndSkill() {
        return (a, b) -> daoMapper.convert(a, b);
    }

    /**
     * Publish message to kafka once it is validated
     *
     * @param message
     * @return
     */
    private ResponseDto publishToKafka(ResponseDto message) {
        if (message.getStatus().equals(Status.CREATED.label))
            producer.sendMessage(SampleProducer.TOPIC, dtoMapper.responseToRequest(message));
        return message;
    }

}

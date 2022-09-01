package com.bootcampzapien.assesment_1.service;

import com.bootcampzapien.assesment_1.dto.RequestDto;
import com.bootcampzapien.assesment_1.dto.ResponseDto;
import com.bootcampzapien.assesment_1.mapper.DaoMapper;
import com.bootcampzapien.assesment_1.mapper.DtoMapper;
import com.bootcampzapien.assesment_1.publisher.SampleProducer;
import com.bootcampzapien.assesment_1.repository.EmployeeDataRepository;
import com.bootcampzapien.assesment_1.repository.SkillRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

import reactor.kafka.sender.KafkaSender;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDataRepository employeeDataRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private DaoMapper daoMapper;
    @Autowired
    private DtoMapper dtoMapper;

    //Setup Properties for Kafka Producer
    final String BOOTSTRAP_SERVERS = "localhost:9092";
    final String TOPIC = "app_updates";

    KafkaSender<Integer, String> sender;
    DateTimeFormatter dateFormat;

    int count = 20;
    CountDownLatch latch = new CountDownLatch(count);

    SampleProducer producer = new SampleProducer(BOOTSTRAP_SERVERS);



    /**
     * creates a new employee
     *
     * @param newRequestDtoDto
     * @return Employee mono
     */
    public Mono<ResponseDto> createEmployee(RequestDto newRequestDtoDto) {
        return employeeDataRepository.existsById(newRequestDtoDto.getEmp_id())
                .flatMap(exists -> {
                    String message = exists ? "Already Exists" : "Created";
                    return Mono
                            .zip(
                                    exists ? employeeDataRepository.findById(newRequestDtoDto.getEmp_id())
                                            : employeeDataRepository.save(this.daoMapper.employeeToEmployeeData(newRequestDtoDto)),
                                    exists ? skillRepository.findById(newRequestDtoDto.getEmp_id())
                                            : skillRepository.save(this.daoMapper.employeeToSkill(newRequestDtoDto)),
                                    (a, b) -> {
                                        return new RequestDto(a.getEmp_id(), a.getEmp_name(), a.getEmp_city(), a.getEmp_phone(), b.getJava_exp(), b.getSpring_exp()
                                        );
                                    }
                            )
                            .map(requestDto -> {
                                        ResponseDto res = dtoMapper.requestToResponse(requestDto);
                                        res.setStatus(message);
                                        return res;
                                    }
                            );
                })
                .map(message -> {
                    try {
                        producer.sendMessages(TOPIC,count,latch);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return message;
                });
    }

}

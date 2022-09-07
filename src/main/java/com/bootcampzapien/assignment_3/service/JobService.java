package com.bootcampzapien.assignment_3.service;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.dto.UserDto;
import com.bootcampzapien.assignment_3.entity.JobData;
import com.bootcampzapien.assignment_3.mapper.DaoMapper;
import com.bootcampzapien.assignment_3.mapper.DtoMapper;
import com.bootcampzapien.assignment_3.repository.JobRepository;
import com.bootcampzapien.assignment_3.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private DaoMapper daoMapper;
    @Autowired
    private DtoMapper dtoMapper;

    WebClient client = WebClient.create("http://localhost:8080");

    /**
     * creates a new employee
     *
     * @param newRequestDtoDto
     * @return Employee Mono
     */
    public Mono<ResponseDto> createJobProfile(RequestDto newRequestDtoDto) {
        log.info("Creating new job profile");
        return jobRepository.existsById(newRequestDtoDto.getJob_id())
                .flatMap(exists -> {
                    String message = exists ? Status.ALREADY_EXISTS.label : Status.CREATED.label;
                    return (exists ? jobRepository.findById(newRequestDtoDto.getJob_id())
                            : jobRepository.save(this.daoMapper.requestToJob(newRequestDtoDto)))
                            .map(jobData -> daoMapper.JobToResponse(jobData, message));
                });
    }

    public Flux<UserDto> findEmpForJobID(RequestDto requestDto) {
//        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
//        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/findEmpSkillset");
//        UserDto user = new UserDto();
//        user.setJava_exp(1.0);
         jobRepository.findAll().subscribe(list-> System.out.println(list));


        return client.method(HttpMethod.GET)
                .uri("/findEmpSkillset")
                .body(jobRepository.findById(requestDto.getJob_id())
                                .map(response -> daoMapper.JobToResponse(response, "created"))
                        , ResponseDto.class)
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}

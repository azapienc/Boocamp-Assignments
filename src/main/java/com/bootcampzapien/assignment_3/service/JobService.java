package com.bootcampzapien.assignment_3.service;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.dto.UserDto;
import com.bootcampzapien.assignment_3.entity.JobData;
import com.bootcampzapien.assignment_3.mapper.DaoMapper;
import com.bootcampzapien.assignment_3.mapper.DtoMapper;
import com.bootcampzapien.assignment_3.repository.JobRepository;
import com.bootcampzapien.assignment_3.utils.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private DaoMapper daoMapper;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private HazelcastInstance hazelcastInstance;
    @Autowired
    private ObjectMapper objectMapper;

    private ConcurrentMap<String, String> retrieveMap() {
        return hazelcastInstance.getMap("map");
    }


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
                            .map(jobData -> daoMapper.jobToResponse(jobData, message));
                });
    }

    public Flux<UserDto> findEmpForJobID(RequestDto requestDto) {
        return client.method(HttpMethod.GET)
                .uri("/findEmpSkillset")
                .body(jobRepository
                                .findById(requestDto.getJob_id())
                                .map(response -> daoMapper.jobToRequest(response))
                        , RequestDto.class)
                .retrieve()
                .bodyToFlux(UserDto.class);
    }

    //    @Cacheable("cache")
    public Mono<RequestDto> getJobProfileFromCache(RequestDto requestDto) throws JsonProcessingException {
        if (retrieveMap().containsKey(String.valueOf(requestDto.getJob_id()))) {
            log.info("Reading request from cache" + retrieveMap().get(String.valueOf(requestDto.getJob_id())));
            return Mono.just(objectMapper.readValue(
                    retrieveMap().get(String.valueOf(requestDto.getJob_id()))
                    , RequestDto.class));
        } else {
            log.info("Reading request from database and updating cache");

            return jobRepository
                    .findById(requestDto.getJob_id())
                    .map(jobData -> daoMapper.jobToRequest(jobData))
                    .map(requestDto1 -> {
                        try {
                            retrieveMap().put(String.valueOf(requestDto.getJob_id()), objectMapper.writeValueAsString(requestDto1));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        return  requestDto1;
                    });
        }
    }
}

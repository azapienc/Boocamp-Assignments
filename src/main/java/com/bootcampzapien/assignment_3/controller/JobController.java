package com.bootcampzapien.assignment_3.controller;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.dto.UserDto;
import com.bootcampzapien.assignment_3.entity.JobData;
import com.bootcampzapien.assignment_3.service.JobService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("createJobProfile")
    @Validated
    public Mono<ResponseDto> createJobProfile(@Valid @RequestBody RequestDto requestDto) {
        return this.jobService.createJobProfile(requestDto);
    }

    @GetMapping("findEmpForJobID")
    public Flux<UserDto> findEmpForJobID(@RequestBody RequestDto requestDto) {
        return this.jobService.findEmpForJobID(requestDto);
    }

    @GetMapping("getJobProfileFromCache")
    public Mono<RequestDto> getJobProfileFromCache(@RequestBody RequestDto requestDto) throws JsonProcessingException {
        return this.jobService.getJobProfileFromCache(requestDto);
    }
}

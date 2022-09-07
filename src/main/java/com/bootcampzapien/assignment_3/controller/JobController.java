package com.bootcampzapien.assignment_3.controller;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.dto.EmployeeDto;
import com.bootcampzapien.assignment_3.service.JobService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    /**
     * creates a new Job profile
     *
     * @param requestDto
     * @return Created job profile
     */
    @PostMapping("createJobProfile")
    @Validated
    public Mono<ResponseDto> createJobProfile(@Valid @RequestBody RequestDto requestDto) {
        return this.jobService.createJobProfile(requestDto);
    }

    /**
     * Find employees through webclient that meets the required experience
     *
     * @param requestDto
     * @return Founded employees
     */
    @GetMapping("findEmpForJobID")
    public Flux<EmployeeDto> findEmpForJobID(@RequestBody RequestDto requestDto) {
        return this.jobService.findEmpForJobID(requestDto);
    }

    /**
     * Get Job profiles from cache, if the profile does not exist then save it.
     *
     * @param requestDto
     * @return Founded Job profile from cache or database
     * @throws JsonProcessingException
     */
    @GetMapping("getJobProfileFromCache")
    public Mono<RequestDto> getJobProfileFromCache(@RequestBody RequestDto requestDto) throws JsonProcessingException {
        return this.jobService.getJobProfileFromCache(requestDto);
    }
}

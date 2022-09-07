package com.bootcampzapien.assignment_3.controller;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping("findEmpSkillset")
//    public Flux<RequestDto> findEmpSkillset(@RequestBody RequestDto requestDto) throws BootcampExperienceException {
//        return this.jobService.findEmpSkillset(requestDto);
//    }
}

package com.bootcampzapien.assignment_1.controller;

import com.bootcampzapien.assignment_1.dto.RequestDto;
import com.bootcampzapien.assignment_1.dto.ResponseDto;
import com.bootcampzapien.assignment_1.exception.BootcampExperienceException;
import com.bootcampzapien.assignment_1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates a new employee
     *
     * @param requestDto
     * @return Created employee
     */
    @PostMapping("createEmployee")
    @Validated
    public Mono<ResponseDto> createEmployee(@Valid @RequestBody RequestDto requestDto) {
        return this.employeeService.createEmployee(requestDto);
    }

    /**
     * Creates a new employee without any validation, for testing purposes only
     *
     * @param requestDto
     * @return Created employee
     */
    @PostMapping("createEmployeeWithoutValidation")
    @Validated
    public Mono<ResponseDto> createEmployeeWithoutValidation(@RequestBody RequestDto requestDto) {
        return this.employeeService.createEmployee(requestDto);
    }

    /**
     * Look for users with matching skills
     *
     * @param requestDto
     * @return Employee that meets the input experience
     * @throws BootcampExperienceException
     */
    @GetMapping("findEmpSkillset")
    public Flux<RequestDto> findEmpSkillset(@RequestBody RequestDto requestDto) throws BootcampExperienceException {
        return this.employeeService.findEmpSkillset(requestDto);
    }
}

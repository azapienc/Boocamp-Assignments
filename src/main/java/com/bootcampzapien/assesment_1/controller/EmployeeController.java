package com.bootcampzapien.assesment_1.controller;

import com.bootcampzapien.assesment_1.dto.Employee;
import com.bootcampzapien.assesment_1.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("createEmployee")
    public Mono<Employee> createEmployee(@RequestBody Employee newEmployee) {
        log.info("Creating new employee");
        return this.employeeService.createEmployee(newEmployee);
    }

}

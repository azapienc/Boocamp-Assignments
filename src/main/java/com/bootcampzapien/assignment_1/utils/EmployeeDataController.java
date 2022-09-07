package com.bootcampzapien.assignment_1.utils;

import com.bootcampzapien.assignment_1.entity.EmployeeData;
import com.bootcampzapien.assignment_1.service.EmployeeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class EmployeeDataController {
    @Autowired
    private EmployeeDataService employeeDataService;

    @GetMapping("getAllEmployees")
    public Flux<EmployeeData> getAllEmployees(){
        return employeeDataService.getAllEmployees();
    }

    @GetMapping("findById/{id}")
    public Mono<EmployeeData> findById(@PathVariable int id){
        return employeeDataService.getEmployeeById(id);
    }

    @DeleteMapping("deleteEmployee/{id}")
    public Mono<Void> getAllEmployees(@PathVariable int id){
        return employeeDataService.deleteEmployee(id);
    }
}

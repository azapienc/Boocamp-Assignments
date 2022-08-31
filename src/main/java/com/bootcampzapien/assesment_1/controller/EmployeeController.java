package com.bootcampzapien.assesment_1.controller;

import com.bootcampzapien.assesment_1.dto.Employee;
import com.bootcampzapien.assesment_1.model.EmployeeData;
import com.bootcampzapien.assesment_1.model.Skill;
import com.bootcampzapien.assesment_1.repository.EmployeeDataRepository;
import com.bootcampzapien.assesment_1.repository.SkillRepository;
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
    @Autowired
    private EmployeeDataRepository employeeDataRepository;
    @Autowired
    private SkillRepository skillRepository;

    @PostMapping("createEmployee")
    public Mono<Employee> createEmployee(@RequestBody Employee newEmployee) {
        log.info("Creating new employee");
        EmployeeData newEmployeeData = new EmployeeData(
                newEmployee.getEmp_id(),
                newEmployee.getEmp_name(),
                newEmployee.getEmp_city(),
                newEmployee.getEmp_phone());
        Skill newSkill = new Skill(
                newEmployee.getEmp_id(),
                newEmployee.getJava_exp(),
                newEmployee.getSpring_exp()
        );

        return Mono.zip(
                employeeDataRepository.save(newEmployeeData),
                skillRepository.save(newSkill),
                (a,b)->{
                    return new Employee(
                            a.getEmp_id(),
                            a.getEmp_name(),
                            a.getEmp_city(),
                            a.getEmp_phone(),
                            b.getJava_exp(),
                            b.getSpring_exp()
                    );
                }
        );
    }
//    @PostMapping("createEmployee")
//    public Mono<Employee> createEmployee(@RequestBody Employee newEmployee) {
//        log.info("Creating new employee");
//        return employeeService.createEmployee(newEmployee);
//    }

//    /**
    //     * Initializer for some employees after service injection
    //     */
//    @PostConstruct
//    public void saveEmployees(){
//        List<Employee> employees = new ArrayList<>();
//        employees.add(new Employee(1,"Armando","México","55225444361"));
//        employeeService.initializeEmployees(employees);
//    }
}

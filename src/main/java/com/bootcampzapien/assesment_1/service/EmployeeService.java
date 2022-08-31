package com.bootcampzapien.assesment_1.service;

import com.bootcampzapien.assesment_1.dto.Employee;
import com.bootcampzapien.assesment_1.model.EmployeeData;
import com.bootcampzapien.assesment_1.model.Skill;
import com.bootcampzapien.assesment_1.repository.EmployeeRepository;
import com.bootcampzapien.assesment_1.repository.EmployeeDataRepository;
import com.bootcampzapien.assesment_1.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeDataRepository employeeDataRepository;
    @Autowired
    private SkillRepository skillRepository;

    /**
     * creates a new employee
     *
     * @param newEmployeeDto
     * @return Employee mono
     */
    public Mono<Employee> createEmployee(Employee newEmployeeDto) {
        EmployeeData newEmployeeData = new EmployeeData(
                newEmployeeDto.getEmp_id(),
                newEmployeeDto.getEmp_name(),
                newEmployeeDto.getEmp_city(),
                newEmployeeDto.getEmp_phone());
        Skill newSkill = new Skill(
                newEmployeeDto.getEmp_id(),
                newEmployeeDto.getJava_exp(),
                newEmployeeDto.getSpring_exp()
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
}

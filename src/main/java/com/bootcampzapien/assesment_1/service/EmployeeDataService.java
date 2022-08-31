package com.bootcampzapien.assesment_1.service;

import com.bootcampzapien.assesment_1.model.EmployeeData;
import com.bootcampzapien.assesment_1.repository.EmployeeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EmployeeDataService {
    @Autowired
    private EmployeeDataRepository employeeDataRepository;

    /**
     * Function to hardcode data in database
     */
    public void initializeEmployees(List<EmployeeData> employeeData) {
        Flux<EmployeeData> savedEmployees = employeeDataRepository.saveAll(employeeData);
        savedEmployees.subscribe();
    }

    /**
     * Get list of all employees
     * @return employee flux
     */
    public Flux<EmployeeData> getAllEmployees(){
        return employeeDataRepository.findAll();
    }

    /**
     * Get employee by id
     * @param id employee id
     * @return employee mono
     */
    public Mono<EmployeeData> getEmployeeById(int id) {
        return employeeDataRepository.findById(id);
    }

    /**
     * creates a new employee
     * @param newEmployeeData
     * @return Employee mono
     */
    public Mono<EmployeeData> createEmployee(EmployeeData newEmployeeData){
        return employeeDataRepository.save(newEmployeeData);
    }

    public Mono<Void> deleteEmployee(int id){
        return employeeDataRepository.deleteById(id);
    }
}

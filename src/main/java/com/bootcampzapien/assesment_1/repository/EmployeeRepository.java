package com.bootcampzapien.assesment_1.repository;

import com.bootcampzapien.assesment_1.dto.Employee;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface EmployeeRepository extends ReactiveCassandraRepository<Employee, Integer> {
}

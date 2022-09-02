package com.bootcampzapien.assignment_1.repository;

import com.bootcampzapien.assignment_1.entity.EmployeeData;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDataRepository extends ReactiveCassandraRepository<EmployeeData, Integer> {
}

package com.bootcampzapien.assesment_1.repository;

import com.bootcampzapien.assesment_1.entity.EmployeeData;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface EmployeeDataRepository extends ReactiveCassandraRepository<EmployeeData, Integer> {
}

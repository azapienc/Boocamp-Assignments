package com.bootcampzapien.assignment_3.repository;

import com.bootcampzapien.assignment_3.entity.JobData;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends ReactiveCassandraRepository<JobData, Integer> {
}

package com.bootcampzapien.assignment_1.repository;

import com.bootcampzapien.assignment_1.entity.Skill;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SkillRepository extends ReactiveCassandraRepository<Skill, Integer> {

    @AllowFiltering
    Flux<Skill> findByJavaExpGreaterThan(double javaExp);

    @AllowFiltering
    Flux<Skill> findBySpringExpGreaterThan(double springExp);

}

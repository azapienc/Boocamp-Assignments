package com.bootcampzapien.assignment_1.repository;

import com.bootcampzapien.assignment_1.entity.Skill;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SkillRepository extends ReactiveCassandraRepository<Skill, Integer> {

    @AllowFiltering
    Flux<Skill> findByJavaExpGreaterThanEqual(double javaExp);

    @AllowFiltering
    Flux<Skill> findBySpringExpGreaterThanEqual(double springExp);

    @AllowFiltering
    Flux<Skill> findByJavaExpGreaterThanEqualAndSpringExpGreaterThanEqual(double javaExp, double springExp);

}

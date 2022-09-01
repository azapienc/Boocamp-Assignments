package com.bootcampzapien.assesment_1.repository;

import com.bootcampzapien.assesment_1.entity.Skill;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SkillRepository extends ReactiveCassandraRepository<Skill, Integer> {

    @AllowFiltering
    Flux<Skill> findByJavaExpGreaterThan(double javaExp);

    @AllowFiltering
    Flux<Skill> findBySpringExpGreaterThan(double springExp);

    @AllowFiltering
    Flux<Skill> findByJavaExpAndSpringExpGreaterThan(double javaExp, double springExp);

}

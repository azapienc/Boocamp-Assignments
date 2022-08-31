package com.bootcampzapien.assesment_1.repository;

import com.bootcampzapien.assesment_1.model.Skill;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SkillRepository extends ReactiveCassandraRepository<Skill, Integer> {

}

package com.bootcampzapien.assesment_1.repository;

import com.bootcampzapien.assesment_1.entity.Skill;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface SkillRepository extends ReactiveCassandraRepository<Skill, Integer> {

}

package com.bootcampzapien.assesment_1.service;

import com.bootcampzapien.assesment_1.entity.Skill;
import com.bootcampzapien.assesment_1.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;
    /**
     *
     * Get list of all employees
     * @return employee flux
     */
    public Flux<Skill> getAllSkills(){
        return skillRepository.findAll();
    }

    /**
     * Get employee by id
     * @param id employee id
     * @return employee mono
     */
    public Mono<Skill> getSkillById(int id) {
        return skillRepository.findById(id);
    }

    public Flux<Skill> findGreaterThan(double id) {
        return skillRepository.findByJavaExpGreaterThan(id);
    }

    /**
     * creates a new employee
     * @param newEmployee
     * @return Employee mono
     */
    public Mono<Skill> createSkill(Skill newEmployee){
        return skillRepository.save(newEmployee);
    }

    public Mono<Void> deleteSkill(Skill skill){
        return skillRepository.delete(skill);
    }
}

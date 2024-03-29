package com.bootcampzapien.assignment_1.utils;

import com.bootcampzapien.assignment_1.entity.Skill;
import com.bootcampzapien.assignment_1.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("getAllSkills")
    public Flux<Skill> getAllEmployees() {
        return skillService.getAllSkills();
    }

    @GetMapping("findSkillById/{id}")
    public Mono<Skill> findSkillById(@PathVariable int id){
        return skillService.getSkillById(id);
    }

    @GetMapping("findGreaterThan/{id}")
    public Flux<Skill> findGreaterThan(@PathVariable double id){
        return skillService.findGreaterThan(id);
    }

    @DeleteMapping("deleteSkill")
    public Mono<Void> getAllEmployees(@RequestBody Skill skill){
        System.out.println(skill);
        return skillService.deleteSkill(skill);
    }
}

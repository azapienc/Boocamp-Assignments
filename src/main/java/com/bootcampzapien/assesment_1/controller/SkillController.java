package com.bootcampzapien.assesment_1.controller;

import com.bootcampzapien.assesment_1.entity.Skill;
import com.bootcampzapien.assesment_1.service.SkillService;
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

    @DeleteMapping("deleteSkill")
    public Mono<Void> getAllEmployees(@RequestBody Skill skill){
        System.out.println(skill);
        return skillService.deleteSkill(skill);
    }
}

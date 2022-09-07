package com.bootcampzapien.assignment_3.controller;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.entity.JobData;
import com.bootcampzapien.assignment_3.mapper.DaoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;

@RestController
public class CommandController {
    @Autowired
    private HazelcastInstance hazelcastInstance;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DaoMapper daoMapper;

    private ConcurrentMap<String, String> retrieveMap() {
        return hazelcastInstance.getMap("map");
    }

    @PostMapping("/put")
    public JobData put(@RequestParam(value = "key") String key, @RequestBody RequestDto value) throws JsonProcessingException {
        retrieveMap().put(key, objectMapper.writeValueAsString(value));
        return daoMapper.requestToJob(value);
    }

//    @GetMapping("/get")
//    public RequestDto get(@RequestParam(value = "key") String key) throws JsonProcessingException {
//        System.out.println(retrieveMap().containsKey(key));
//        return objectMapper.readValue(retrieveMap().get(key), RequestDto.class);
//    }

    @GetMapping("/get")
    public RequestDto getOrSave(@RequestParam(value = "key") String key, @RequestBody RequestDto value) throws JsonProcessingException {
//        int job_id = objectMapper.readValue(retrieveMap().get(key), RequestDto.class).getJob_id();
        if (retrieveMap().containsKey(key))
            return objectMapper.readValue(retrieveMap().get(key), RequestDto.class);
        else {
            retrieveMap().put(key, objectMapper.writeValueAsString(value));
            return value;
        }
    }
}
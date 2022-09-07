package com.bootcampzapien.assignment_3.service;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.mapper.DaoMapper;
import com.bootcampzapien.assignment_3.mapper.DtoMapper;
import com.bootcampzapien.assignment_3.repository.JobRepository;
import com.bootcampzapien.assignment_3.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private DaoMapper daoMapper;
    @Autowired
    private DtoMapper dtoMapper;

    /**
     * creates a new employee
     *
     * @param newRequestDtoDto
     * @return Employee Mono
     */
    public Mono<ResponseDto> createJobProfile(RequestDto newRequestDtoDto) {
        log.info("Creating new job profile");
        return jobRepository.existsById(newRequestDtoDto.getJob_id())
                .flatMap(exists -> {
                    String message = exists ? Status.ALREADY_EXISTS.label : Status.CREATED.label;
                    return (exists ? jobRepository.findById(newRequestDtoDto.getJob_id())
                            : jobRepository.save(this.daoMapper.requestToJob(newRequestDtoDto)))
                            .map(jobData -> daoMapper.JobToResponse(jobData, message));
                });
    }
}

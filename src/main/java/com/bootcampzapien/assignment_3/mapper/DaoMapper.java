package com.bootcampzapien.assignment_3.mapper;

import com.bootcampzapien.assignment_3.dto.RequestDto;
import com.bootcampzapien.assignment_3.dto.ResponseDto;
import com.bootcampzapien.assignment_3.entity.JobData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DaoMapper {

    DaoMapper INSTANCE = Mappers.getMapper(DaoMapper.class);

    @Mapping(source = "job_id", target = "jobId")
    @Mapping(source = "job_name", target = "jobName")
    @Mapping(source = "java_exp", target = "javaExp")
    @Mapping(source = "spring_exp", target = "springExp")
    JobData requestToJob(RequestDto requestDto);

    @Mapping(source = "jobData.jobId", target = "job_id")
    @Mapping(source = "jobData.jobName", target = "job_name")
    @Mapping(source = "jobData.javaExp", target = "java_exp")
    @Mapping(source = "jobData.springExp", target = "spring_exp")
    @Mapping(source = "status",target = "status")
    ResponseDto JobToResponse(JobData jobData, String status);
}

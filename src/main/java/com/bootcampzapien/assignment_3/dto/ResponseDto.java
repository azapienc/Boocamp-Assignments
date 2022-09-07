package com.bootcampzapien.assignment_3.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private int job_id;
    private String job_name;
    private Double java_exp;
    private Double spring_exp;
    private String status;
}
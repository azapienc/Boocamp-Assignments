package com.bootcampzapien.assesment_1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDto {
    private int emp_id;
    private String emp_name;
    private String emp_city;
    private String emp_phone;
    private Double java_exp;
    private Double spring_exp;
}

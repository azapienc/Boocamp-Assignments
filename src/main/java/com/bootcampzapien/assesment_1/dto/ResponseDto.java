package com.bootcampzapien.assesment_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private int emp_id;
    private String emp_name;
    private String emp_city;
    private String emp_phone;
    private Double java_exp;
    private Double spring_exp;
    private String status;
}

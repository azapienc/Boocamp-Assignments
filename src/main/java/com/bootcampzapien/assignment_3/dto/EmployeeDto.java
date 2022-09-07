package com.bootcampzapien.assignment_3.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private int emp_id;
    private String emp_name;
    private String emp_city;
    private String emp_phone;
    private Double java_exp;
    private Double spring_exp;
}

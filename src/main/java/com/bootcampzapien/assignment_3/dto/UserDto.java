package com.bootcampzapien.assignment_3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private int emp_id;
    private String emp_name;
    private String emp_city;
    private String emp_phone;
    private Double java_exp;
    private Double spring_exp;
}

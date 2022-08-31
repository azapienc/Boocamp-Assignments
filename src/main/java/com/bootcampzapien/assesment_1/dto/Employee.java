package com.bootcampzapien.assesment_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @PrimaryKey
    private int emp_id;
    private String emp_name;
    private String emp_city;
    private String emp_phone;
    private Double java_exp;
    private Double spring_exp;
}

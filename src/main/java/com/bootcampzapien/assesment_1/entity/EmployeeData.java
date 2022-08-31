package com.bootcampzapien.assesment_1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("emp")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeData {
    @PrimaryKey
    private int emp_id;
    @Column(value = "emp_name")
    private String emp_name;
    @Column(value = "emp_city")
    private String emp_city;
    @Column(value = "emp_phone")
    private String emp_phone;
}
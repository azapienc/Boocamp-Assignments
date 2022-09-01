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
    @Column(value = "emp_id")
    private int empId;
    @Column(value = "emp_name")
    private String empName;
    @Column(value = "emp_city")
    private String empCity;
    @Column(value = "emp_phone")
    private String empPhone;
}
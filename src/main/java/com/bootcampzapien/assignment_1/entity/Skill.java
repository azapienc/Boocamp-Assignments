package com.bootcampzapien.assignment_1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("emp_skill")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    @PrimaryKey
    @Column(value = "emp_id")
    private int empId;
    @Column(value = "java_exp")
    private Double javaExp;
    @Column(value = "spring_exp")
    private Double springExp;
}

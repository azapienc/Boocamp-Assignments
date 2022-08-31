package com.bootcampzapien.assesment_1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("emp_skill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    @PrimaryKey
    private int emp_id;
    private Double java_exp;
    private Double spring_exp;
}

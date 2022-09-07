package com.bootcampzapien.assignment_3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table("job")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobData{
    @PrimaryKey
    @Column(value = "job_id")
    private int jobId;
    @Column(value = "job_name")
    private String jobName;
    @Column(value = "java_exp")
    private String javaExp;
    @Column(value = "spring_exp")
    private String springExp;
}
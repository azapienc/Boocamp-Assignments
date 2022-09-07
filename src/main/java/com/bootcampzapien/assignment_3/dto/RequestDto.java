package com.bootcampzapien.assignment_3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto implements Serializable {
    @NotNull(message = "{Request.ID.INVALID}")
    private int job_id;
    @NotNull(message = "{Request.NAME.INVALID}")
    private String job_name;
    @NotNull(message = "{Request.JAVAEXP.INVALID}")
    private Double java_exp;
    @NotNull(message = "{Request.SPRINGEXP.INVALID}")
    private Double spring_exp;
}

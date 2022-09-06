package com.bootcampzapien.assignment_1.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class RequestDto {
    @NotNull(message = "{Request.ID.INVALID}")
    private int emp_id;
    @NotNull(message = "{Request.NAME.INVALID}")
    private String emp_name;
    @NotNull(message = "{Request.CITY.INVALID}")
    private String emp_city;
    @NotNull(message = "{Request.PHONE.INVALID}")
    private String emp_phone;
    @NotNull(message = "{Request.JAVAEXP.INVALID}")
    private Double java_exp;
    @NotNull(message = "{Request.SPRINGEXP.INVALID}")
    private Double spring_exp;
}

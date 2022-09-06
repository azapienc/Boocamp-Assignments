package com.bootcampzapien.assignment_1.mapper;

import com.bootcampzapien.assignment_1.dto.RequestDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RequestDtoJSONSerializer extends JsonSerializer<RequestDto> {
    @Override
    public void serialize(RequestDto value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("emp_id", String.valueOf(value.getEmp_id()));
        gen.writeStringField("emp_name", value.getEmp_name());
        gen.writeStringField("emp_city", value.getEmp_city());
        gen.writeStringField("emp_phone", value.getEmp_phone());
        gen.writeStringField("java_exp", String.valueOf(value.getJava_exp()));
        gen.writeStringField("spring_exp", String.valueOf(value.getSpring_exp()));
        gen.writeEndObject();
    }
}

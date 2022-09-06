package com.bootcampzapien.assignment2.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RequestDtoJSONDeserializer extends JsonDeserializer<RequestDto> {
    @Override
    public RequestDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        TreeNode treeNode = p.getCodec().readTree(p);
        RequestDto request = new RequestDto();
        request.setEmp_id(((TextNode) treeNode.get("emp_id")).asInt());
        request.setEmp_name(((TextNode) treeNode.get("emp_name")).asText());
        request.setEmp_city(((TextNode) treeNode.get("emp_city")).asText());
        request.setEmp_phone(((TextNode) treeNode.get("emp_phone")).asText());
        request.setJava_exp(((TextNode) treeNode.get("java_exp;")).asDouble());
        request.setSpring_exp(((TextNode) treeNode.get("spring_ex")).asDouble());
        return request;
    }
}

package com.bootcampzapien.assesment_1.mapper;

import com.bootcampzapien.assesment_1.dto.RequestDto;
import com.bootcampzapien.assesment_1.entity.EmployeeData;
import com.bootcampzapien.assesment_1.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DaoMapper {

    DaoMapper INSTANCE = Mappers.getMapper(DaoMapper.class);

    @Mapping(source = "emp_id", target = "empId")
    @Mapping(source = "emp_name", target = "empName")
    @Mapping(source = "emp_city", target = "empCity")
    @Mapping(source = "emp_phone", target = "empPhone")
    EmployeeData requestToEmployeeData(RequestDto requestDto);

    @Mapping(source = "emp_id", target = "empId")
    @Mapping(source = "java_exp", target = "javaExp")
    @Mapping(source = "spring_exp", target = "springExp")
    Skill requestToSkill(RequestDto requestDto);

    @Mapping(source = "employeeData.empId", target = "emp_id")
    @Mapping(source = "employeeData.empName", target = "emp_name")
    @Mapping(source = "employeeData.empCity", target = "emp_city")
    @Mapping(source = "employeeData.empPhone", target = "emp_phone")
    @Mapping(source = "skill.javaExp", target = "java_exp")
    @Mapping(source = "skill.springExp", target = "spring_exp")
    RequestDto convert(EmployeeData employeeData, Skill skill);

}

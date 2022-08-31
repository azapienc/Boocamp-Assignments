package com.bootcampzapien.assesment_1.mapper;

import com.bootcampzapien.assesment_1.dto.Employee;
import com.bootcampzapien.assesment_1.entity.EmployeeData;
import com.bootcampzapien.assesment_1.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DaoMapper {

    DaoMapper INSTANCE = Mappers.getMapper(DaoMapper.class);

    EmployeeData employeeToEmployeeData(Employee employee);

    Skill employeeToSkill(Employee employee);

}

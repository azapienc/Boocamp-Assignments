package com.bootcampzapien.assignment_1.mapper;

import com.bootcampzapien.assignment_1.dto.RequestDto;
import com.bootcampzapien.assignment_1.dto.ResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    DaoMapper INSTANCE = Mappers.getMapper(DaoMapper.class);

    RequestDto responseToRequest(ResponseDto responseDto);

    @Mapping(source = "status",target = "status")
    ResponseDto requestToResponse(RequestDto requestDto, String status);

    RequestDto clean(RequestDto requestDto);

}

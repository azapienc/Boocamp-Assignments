package com.bootcampzapien.assesment_1.mapper;

import com.bootcampzapien.assesment_1.dto.RequestDto;
import com.bootcampzapien.assesment_1.dto.ResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    DaoMapper INSTANCE = Mappers.getMapper(DaoMapper.class);

    RequestDto responseToRequest(ResponseDto responseDto);

    ResponseDto requestToResponse(RequestDto requestDto);

}
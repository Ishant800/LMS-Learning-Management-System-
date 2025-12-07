package com.example.product_service.mapper;

import com.example.product_service.Dto.TeacherDto;
import com.example.product_service.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toEntity(TeacherDto dto);

    TeacherDto toDto(Teacher teacher);
}

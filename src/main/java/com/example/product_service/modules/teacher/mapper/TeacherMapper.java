package com.example.product_service.modules.teacher.mapper;

import com.example.product_service.modules.teacher.dto.TeacherDto;
import com.example.product_service.modules.teacher.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toEntity(TeacherDto dto);

    TeacherDto toDto(Teacher teacher);
}

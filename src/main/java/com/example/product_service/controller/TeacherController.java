package com.example.product_service.controller;

import com.example.product_service.Dto.TeacherDto;
import com.example.product_service.entity.Teacher;
import com.example.product_service.repository.TeacherRepository;
import com.example.product_service.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;

    }

    @PostMapping("/createTeacher")
    public ResponseEntity<Teacher> createTeacher(@RequestBody TeacherDto dto){
        return ResponseEntity.ok(teacherService.createTeacher(dto));
    }
}

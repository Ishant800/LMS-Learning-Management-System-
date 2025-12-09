package com.example.product_service.controller;

import com.example.product_service.Dto.TeacherDto;
import com.example.product_service.entity.Teacher;
import com.example.product_service.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id){
       return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping("/allTeachers")
    public ResponseEntity<List<Teacher>> getTeachers(){
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
}

package com.example.product_service.modules.student.controller;

import com.example.product_service.modules.student.dto.StudentDto;
import com.example.product_service.modules.student.entity.Student;
import com.example.product_service.modules.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto dto){
        return ResponseEntity.ok(service.createStudent(dto));
    }

    @GetMapping("/getStudents")
    public ResponseEntity<List<Student>> getALlStudents(){
        return ResponseEntity.ok(service.getStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(service.getStudentById(id));
    }
}

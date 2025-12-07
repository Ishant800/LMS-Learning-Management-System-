package com.example.product_service.controller;

import com.example.product_service.Dto.CourseDto;
import com.example.product_service.entity.Course;
import com.example.product_service.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/addcourse")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDto dto){
        return ResponseEntity.ok(courseService.createCourseWithoutSubject(dto));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourse(){
        return ResponseEntity.ok(courseService.getAllCourse());
    }
}

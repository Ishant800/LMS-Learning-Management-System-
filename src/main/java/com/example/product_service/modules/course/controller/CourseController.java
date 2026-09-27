package com.example.product_service.modules.course.controller;

import com.example.product_service.modules.course.dto.CourseDto;
import com.example.product_service.modules.course.entity.Course;
import com.example.product_service.modules.course.service.CourseService;
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

    @PostMapping("/addCourse")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDto dto){
        return ResponseEntity.ok(courseService.createCourseWithoutSubject(dto));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourse(){
        return ResponseEntity.ok(courseService.getAllCourse());
    }
}

package com.example.product_service.modules.course.repository;

import com.example.product_service.modules.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course,Long> {
}

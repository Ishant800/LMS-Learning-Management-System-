package com.example.product_service.modules.course.repository;

import com.example.product_service.modules.course.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepo extends JpaRepository<Subject,Long> {
}

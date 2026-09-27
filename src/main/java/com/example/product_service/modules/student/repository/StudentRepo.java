package com.example.product_service.modules.student.repository;

import com.example.product_service.modules.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student ,Long> {

}

package com.example.product_service.repository;

import com.example.product_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student ,Long> {

}

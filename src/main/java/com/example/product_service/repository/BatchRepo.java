package com.example.product_service.repository;

import com.example.product_service.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepo extends JpaRepository<Batch,Long> {
}

package com.example.product_service.modules.batch.repository;

import com.example.product_service.modules.batch.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepo extends JpaRepository<Batch,Long> {
}

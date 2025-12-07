package com.example.product_service.controller;

import com.example.product_service.Dto.BatchDto;
import com.example.product_service.Dto.BatchResponse;
import com.example.product_service.entity.Batch;
import com.example.product_service.service.BatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/createBatch")
    public ResponseEntity<Batch> createBatch(@RequestBody BatchDto dto){
        return ResponseEntity.ok(batchService.createBatch(dto));
    }

    @GetMapping("/getBatches")
    public ResponseEntity<List<Batch>> getBatches(){
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<Batch> getBatch(@PathVariable Long batchId){
        return ResponseEntity.ok(batchService.getBatchById(batchId));
    }
}

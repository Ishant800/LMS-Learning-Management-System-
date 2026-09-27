package com.example.product_service.modules.batch.dto;

public record BatchResponse(
        Long id,
        String batchName,
        String courseName,
        String section,
        int year,
        int totalStudent){

}

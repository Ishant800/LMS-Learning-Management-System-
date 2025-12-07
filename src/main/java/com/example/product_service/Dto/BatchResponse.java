package com.example.product_service.Dto;

public record BatchResponse(
        Long id,
        String batchName,
        String courseName,
        String section,
        int year,
        int totalStudent){

}

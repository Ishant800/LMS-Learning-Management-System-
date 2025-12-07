package com.example.product_service.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchDto {
    private String tenantId;
    private String batchName;
    private Long courseId;
    private Integer totalStudent;
    private Integer year;
    private String section;
}

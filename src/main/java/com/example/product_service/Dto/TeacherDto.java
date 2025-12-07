package com.example.product_service.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherDto {

    private String tenantId;

    private Long userId;

    private String qualification;
    private Integer experienceYears;
    private String specialization;
}

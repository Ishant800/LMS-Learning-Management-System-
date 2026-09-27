package com.example.product_service.modules.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private String tenantId;
    private String courseCode;
    private String courseName;
    private String description;
    private Integer durationsMonths;

    private List<SubjectRequest> subjects;

}

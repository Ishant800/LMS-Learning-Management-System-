package com.example.product_service.modules.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {
    public String tenantId;
    private String subjectName;
    private Long teacherId;
    private String subjectCode;
    private String attendanceId;

}

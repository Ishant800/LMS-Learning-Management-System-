package com.example.product_service.modules.attendance.dto;

import com.example.product_service.common.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDto {
    private Long id;
    private String tenantId;
    private Long studentId;
    private String studentName;
    private Long subjectId;
    private String subjectName;
    private LocalDate date;
    private AttendanceStatus status;
}

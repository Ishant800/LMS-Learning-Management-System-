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
public class AttendanceRequestDto {
    
    private Long studentId;
    private Long subjectId;
    private LocalDate date;
    private AttendanceStatus status;
}

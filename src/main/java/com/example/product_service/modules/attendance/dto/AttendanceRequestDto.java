package com.example.product_service.modules.attendance.dto;

import com.example.product_service.common.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
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
    
    @NotNull(message = "Student ID is required")
    private Long studentId;
    
    @NotNull(message = "Subject ID is required")
    private Long subjectId;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @NotNull(message = "Status is required")
    private AttendanceStatus status;
}

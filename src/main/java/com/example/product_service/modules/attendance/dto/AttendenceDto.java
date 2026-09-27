package com.example.product_service.modules.attendance.dto;


import com.example.product_service.common.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendenceDto {
    private String tenantId;
    private AttendanceStatus status;
    private Long studentId;
    private Long subjectId;

}

package com.example.product_service.modules.attendance.controller;

import com.example.product_service.modules.attendance.dto.AttendanceRequestDto;
import com.example.product_service.modules.attendance.dto.AttendanceResponseDto;
import com.example.product_service.modules.attendance.service.AttendanceService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<AttendanceResponseDto> markAttendance( @RequestBody AttendanceRequestDto requestDto) {
        AttendanceResponseDto response = attendanceService.markAttendance(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{attendanceId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(
            @PathVariable Long attendanceId,
             @RequestBody AttendanceRequestDto requestDto) {
        AttendanceResponseDto response = attendanceService.updateAttendance(attendanceId, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{attendanceId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<AttendanceResponseDto> getAttendanceById(@PathVariable Long attendanceId) {
        AttendanceResponseDto response = attendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendance() {
        List<AttendanceResponseDto> attendances = attendanceService.getAllAttendanceByTenant();
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByStudent(@PathVariable Long studentId) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendanceByStudent(studentId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/subject/{subjectId}/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceBySubjectAndDate(
            @PathVariable Long subjectId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendanceBySubjectAndDate(subjectId, date);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/student/{studentId}/range")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendanceByDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendanceByDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @DeleteMapping("/{attendanceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
}

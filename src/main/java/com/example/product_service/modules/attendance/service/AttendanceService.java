package com.example.product_service.modules.attendance.service;

import com.example.product_service.common.utility.AuthContext;
import com.example.product_service.modules.attendance.dto.AttendanceRequestDto;
import com.example.product_service.modules.attendance.dto.AttendanceResponseDto;
import com.example.product_service.modules.attendance.entity.Attendance;
import com.example.product_service.modules.attendance.repository.AttendanceRepository;
import com.example.product_service.modules.course.entity.Subject;
import com.example.product_service.modules.course.repository.SubjectRepo;
import com.example.product_service.modules.student.entity.Student;
import com.example.product_service.modules.student.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepo studentRepository;
    private final SubjectRepo subjectRepository;
    private final AuthContext authContext;

    @Transactional
    public AttendanceResponseDto markAttendance(AttendanceRequestDto requestDto) {
        String tenantId = authContext.getCurrentTenantId();
        
        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + requestDto.getStudentId()));
        
        Subject subject = subjectRepository.findById(requestDto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + requestDto.getSubjectId()));
        
        // Check if attendance already exists for this student, subject and date
        attendanceRepository.findByStudentIdAndSubjectsIdAndDate(
                requestDto.getStudentId(), 
                requestDto.getSubjectId(), 
                requestDto.getDate()
        ).ifPresent(attendance -> {
            throw new RuntimeException("Attendance already marked for this student on this date");
        });
        
        Attendance attendance = new Attendance();
        attendance.setTenantId(tenantId);
        attendance.setStudent(student);
        attendance.setSubjects(subject);
        attendance.setDate(requestDto.getDate());
        attendance.setStatus(requestDto.getStatus());
        
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return mapToResponseDto(savedAttendance);
    }

    @Transactional
    public AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceRequestDto requestDto) {
        String tenantId = authContext.getCurrentTenantId();
        
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + attendanceId));
        
        if (!attendance.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Unauthorized access to attendance record");
        }
        
        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + requestDto.getStudentId()));
        
        Subject subject = subjectRepository.findById(requestDto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + requestDto.getSubjectId()));
        
        attendance.setStudent(student);
        attendance.setSubjects(subject);
        attendance.setDate(requestDto.getDate());
        attendance.setStatus(requestDto.getStatus());
        
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return mapToResponseDto(updatedAttendance);
    }

    public AttendanceResponseDto getAttendanceById(Long attendanceId) {
        String tenantId = authContext.getCurrentTenantId();
        
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + attendanceId));
        
        if (!attendance.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Unauthorized access to attendance record");
        }
        
        return mapToResponseDto(attendance);
    }

    public List<AttendanceResponseDto> getAllAttendanceByTenant() {
        String tenantId = authContext.getCurrentTenantId();
        List<Attendance> attendances = attendanceRepository.findByTenantId(tenantId);
        return attendances.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDto> getAttendanceByStudent(Long studentId) {
        String tenantId = authContext.getCurrentTenantId();
        List<Attendance> attendances = attendanceRepository.findByTenantIdAndStudentId(tenantId, studentId);
        return attendances.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDto> getAttendanceBySubjectAndDate(Long subjectId, LocalDate date) {
        String tenantId = authContext.getCurrentTenantId();
        List<Attendance> attendances = attendanceRepository.findByTenantIdAndSubjectsIdAndDate(tenantId, subjectId, date);
        return attendances.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDto> getAttendanceByDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        return attendances.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceResponseDto> getAttendanceByDate(LocalDate date) {
        String tenantId = authContext.getCurrentTenantId();
        List<Attendance> attendances = attendanceRepository.findByTenantIdAndDate(tenantId, date);
        return attendances.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAttendance(Long attendanceId) {
        String tenantId = authContext.getCurrentTenantId();
        
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + attendanceId));
        
        if (!attendance.getTenantId().equals(tenantId)) {
            throw new RuntimeException("Unauthorized access to attendance record");
        }
        
        attendanceRepository.delete(attendance);
    }

    private AttendanceResponseDto mapToResponseDto(Attendance attendance) {
        AttendanceResponseDto responseDto = new AttendanceResponseDto();
        responseDto.setId(attendance.getId());
        responseDto.setTenantId(attendance.getTenantId());
        responseDto.setStudentId(attendance.getStudent().getId());
        responseDto.setStudentName(attendance.getStudent().getName());
        responseDto.setSubjectId(attendance.getSubjects().getId());
        responseDto.setSubjectName(attendance.getSubjects().getName());
        responseDto.setDate(attendance.getDate());
        responseDto.setStatus(attendance.getStatus());
        return responseDto;
    }
}

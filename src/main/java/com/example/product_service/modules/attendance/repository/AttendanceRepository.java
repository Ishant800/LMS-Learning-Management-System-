package com.example.product_service.modules.attendance.repository;

import com.example.product_service.modules.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    List<Attendance> findByTenantId(String tenantId);
    
    List<Attendance> findByStudentId(Long studentId);
    
    List<Attendance> findBySubjectsId(Long subjectId);
    
    List<Attendance> findByTenantIdAndStudentId(String tenantId, Long studentId);
    
    List<Attendance> findByTenantIdAndDate(String tenantId, LocalDate date);
    
    Optional<Attendance> findByStudentIdAndSubjectsIdAndDate(Long studentId, Long subjectId, LocalDate date);
    
    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);
    
    List<Attendance> findByTenantIdAndSubjectsIdAndDate(String tenantId, Long subjectId, LocalDate date);
}

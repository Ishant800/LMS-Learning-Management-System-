package com.example.product_service.service;

import com.example.product_service.Dto.AttendenceDto;
import com.example.product_service.entity.Attendance;
import com.example.product_service.entity.Student;
import com.example.product_service.entity.Subject;
import com.example.product_service.entity.Tenant;
import com.example.product_service.exception.UserNotFoundException;
import com.example.product_service.repository.AttendanceRepo;
import com.example.product_service.repository.StudentRepo;
import com.example.product_service.repository.SubjectRepo;
import com.example.product_service.repository.TenantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepo attendanceRepo;
    private final TenantRepository tenantRepository;
    private final SubjectRepo subjectRepo;
    private final StudentRepo studentRepo;

    public AttendanceService(AttendanceRepo attendanceRepo, TenantRepository tenantRepository, SubjectRepo subjectRepo, StudentRepo studentRepo) {
        this.attendanceRepo = attendanceRepo;
        this.tenantRepository = tenantRepository;
        this.subjectRepo = subjectRepo;
        this.studentRepo = studentRepo;
    }

    public Attendance createAttendance(AttendenceDto dto){
        Tenant tenant = tenantRepository.findById(dto.getTenantId()).orElseThrow(()-> new UserNotFoundException("tenant not found!"));
        Subject subject = subjectRepo.findById(dto.getSubjectId()).orElseThrow(()-> new UserNotFoundException("subject not found!"));
        Student student = studentRepo.findById(dto.getStudentId()).orElseThrow(()-> new UserNotFoundException("student not found!"));

        Attendance attendance = new Attendance();
        attendance.setStatus(dto.getStatus());
        attendance.setStudent(student);
        attendance.setSubjects(subject);
        attendance.setTenantId(dto.getTenantId());

        return attendanceRepo.save(attendance);
    }

    public Attendance getAttendanceById(Long id){
        return attendanceRepo.findById(id).orElseThrow(()-> new UserNotFoundException("attendance not found!"));
    }

    public List<Attendance> getAllAttendances(){
        return attendanceRepo.findAll();
    }
}

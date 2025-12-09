package com.example.product_service.service;

import com.example.product_service.Dto.TeacherDto;
import com.example.product_service.entity.Subject;
import com.example.product_service.entity.Tenant;
import com.example.product_service.entity.User;
import com.example.product_service.entity.Teacher;
import com.example.product_service.exception.UserNotFoundException;
import com.example.product_service.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final SubjectRepo subjectRepo;

    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, TenantRepository tenantRepository, SubjectRepo subjectRepo) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
//        this.mapper = mapper;

        this.subjectRepo = subjectRepo;
    }

    public Teacher createTeacher(TeacherDto dto){
       Tenant tenant = tenantRepository.findById(dto.getTenantId()).orElseThrow(()-> new RuntimeException("Organization not found"));
       User user = userRepository.findById(dto.getUserId()).orElseThrow(()-> new RuntimeException("user not found! please register first!"));
        Teacher teacher = new Teacher();

        teacher.setUser(user);
        teacher.setExperienceYears(dto.getExperienceYears());
        teacher.setQualification(dto.getQualification());
        teacher.setSpecialization(dto.getSpecialization());
        teacher.setTenantId(tenant.getId());
        if(dto.getSubjectId() != null ) {
            Subject subject = subjectRepo.findById(dto.getSubjectId()).
                    orElseThrow(()-> new UserNotFoundException("subject not found!"));

            subject.setTeacher(teacher);
            teacher.getSubjects().add(subject);
        }
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id){
        return teacherRepository.findById(id).orElseThrow(()-> new UserNotFoundException("teacher not found"));
    }
}

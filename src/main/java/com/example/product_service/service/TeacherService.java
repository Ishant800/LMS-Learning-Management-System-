package com.example.product_service.service;

import com.example.product_service.Dto.TeacherDto;
import com.example.product_service.entity.Tenant;
import com.example.product_service.entity.User;
import com.example.product_service.entity.Teacher;
import com.example.product_service.repository.TeacherRepository;
import com.example.product_service.repository.TenantRepository;
import com.example.product_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;



    public TeacherService(TeacherRepository teacherRepository, UserRepository userRepository, TenantRepository tenantRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
//        this.mapper = mapper;

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
        return teacherRepository.save(teacher);

    }
}

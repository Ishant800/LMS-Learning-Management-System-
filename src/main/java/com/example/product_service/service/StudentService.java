package com.example.product_service.service;


import com.example.product_service.Dto.StudentDto;
import com.example.product_service.entity.Student;
import com.example.product_service.entity.User;
import com.example.product_service.exception.UserNotFoundException;
import com.example.product_service.repository.StudentRepo;
import com.example.product_service.repository.TenantRepository;
import com.example.product_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    public StudentService(StudentRepo studentRepo, UserRepository userRepository, TenantRepository tenantRepository) {
        this.studentRepo = studentRepo;
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    public Student createStudent(StudentDto dto){
         tenantRepository.findById(dto.getTenantId()).orElseThrow(()-> new UsernameNotFoundException("tenant not found"));
        User user= userRepository.findById(dto.getUserId()).orElseThrow(()-> new UserNotFoundException("user not found create account first"));

        Student student = new Student();
        student.setTenantId(dto.getTenantId());
        student.setUser(user);
        student.setRollNo(dto.getRollNo());
        student.setGuardianName(dto.getGuardianName());
        student.setGuardianPhone(dto.getGuardianPhone());
        student.setGender(dto.getGender());
        student.setAddress(dto.getAddress());
        student.setDob(dto.getDob());

        return studentRepo.save(student);
    }

    public List<Student> getStudents(){
        return studentRepo.findAll();
    }


//    public Student updateStudent(StudentDto dto){
//        tenantRepository.findById(dto.getTenantId()).orElseThrow(()-> new UserNotFoundException("tenant not found!"));
//        Student student = studentRepo.findById(dto.getId()).orElseThrow(()-> new UserNotFoundException("student not found!"));
//
//    }
}

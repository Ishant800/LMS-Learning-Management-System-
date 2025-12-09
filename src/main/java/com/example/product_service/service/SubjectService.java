package com.example.product_service.service;

import com.example.product_service.Dto.SubjectRequest;
import com.example.product_service.entity.Subject;
import com.example.product_service.entity.Teacher;
import com.example.product_service.exception.UserNotFoundException;
import com.example.product_service.repository.SubjectRepo;
import com.example.product_service.repository.TeacherRepository;
import com.example.product_service.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final TeacherRepository teacherRepository;
    private final TenantRepository tenantRepository;

    public SubjectService(SubjectRepo subjectRepo, TeacherRepository teacherRepository, TenantRepository tenantRepository) {
        this.subjectRepo = subjectRepo;
        this.teacherRepository = teacherRepository;
        this.tenantRepository = tenantRepository;
    }

    public Subject createSubject(SubjectRequest dto){
        tenantRepository.findById(dto.tenantId).orElseThrow(()-> new UserNotFoundException("tenant not found!"));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(()-> new UserNotFoundException("teacher not found!"));

        Subject subject = new Subject();
        subject.setSubjectName(dto.getSubjectName());
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setTeacher(teacher);

        return subjectRepo.save(subject);
    }

    public List<Subject> getSubjects(){
        return subjectRepo.findAll();
    }

    public Subject getSubjectById(Long id){
        return subjectRepo.findById(id).orElseThrow(()-> new UserNotFoundException("Subject not found!"));
    }

}

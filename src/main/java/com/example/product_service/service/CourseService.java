package com.example.product_service.service;

import com.example.product_service.Dto.CourseDto;
import com.example.product_service.Dto.SubjectRequest;
import com.example.product_service.entity.Course;
import com.example.product_service.entity.Subject;
import com.example.product_service.entity.Teacher;
import com.example.product_service.repository.CourseRepo;
import com.example.product_service.repository.SubjectRepo;
import com.example.product_service.repository.TeacherRepository;
import com.example.product_service.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepo courseRepo;
    private final TenantRepository tenantRepository;
    private final SubjectRepo subjectRepo;
    private  final TeacherRepository teacherRepo;

    public CourseService(CourseRepo courseRepo, TenantRepository tenantRepository, SubjectRepo subjectRepo, TeacherRepository teacherRepo) {
        this.courseRepo = courseRepo;
        this.tenantRepository = tenantRepository;
        this.subjectRepo = subjectRepo;
        this.teacherRepo = teacherRepo;
    }

    public Course createCourseWithoutSubject(CourseDto dto){
        Course course = new Course();
        course.setTenant(null);
        course.setCourseName(dto.getCourseName());
        course.setCourseCode(dto.getCourseCode());
        course.setDescription(dto.getDescription());
        course.setDurationMonths(dto.getDurationsMonths());

        //add subjects
        if(dto.getSubjects() != null){
            for(SubjectRequest sr: dto.getSubjects()){
                Teacher teacher = teacherRepo.findById(sr.getTeacherId()).orElseThrow(()-> new RuntimeException("teachers not found!"));

                Subject subject = new Subject();
                subject.setSubjectName(sr.getSubjectName());
                subject.setTeacher(teacher);
                subject.setCourse(course);

                course.getSubjects().add(subject);
            }
        }
        return courseRepo.save(course);
    }


    public List<Course> getAllCourse(){
        return courseRepo.findAll();
    }
}

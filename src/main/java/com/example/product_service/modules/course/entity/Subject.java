package com.example.product_service.modules.course.entity;


import com.example.product_service.modules.attendance.entity.Attendance;
import com.example.product_service.modules.teacher.entity.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
//    @JsonBackReference
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "subjects")
    private List<Attendance> attendances = new ArrayList<>();

    private String subjectName;
    private String subjectCode;
    
    public String getName(){
        return subjectName;
    }
}

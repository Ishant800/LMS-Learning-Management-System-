package com.example.product_service.modules.attendance.entity;


import com.example.product_service.common.enums.AttendanceStatus;
import com.example.product_service.modules.course.entity.Subject;
import com.example.product_service.modules.student.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subjects;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}


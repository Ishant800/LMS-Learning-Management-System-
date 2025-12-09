package com.example.product_service.entity;

import com.example.product_service.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(unique = true)
    private String rollNo;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private String guardianName;
    private String guardianPhone;

    @Column(updatable = false)
    private LocalDateTime admissionDate;
    private Boolean active = true;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances = new ArrayList<>();

    @PrePersist
    public void onCreate(){
         admissionDate = LocalDateTime.now();
    }
}


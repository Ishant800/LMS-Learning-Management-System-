package com.example.product_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses",indexes = {
        @Index(name = "idx_course_tenant",columnList = "tenant_id"),
        @Index(name = "idx_course_code",columnList = "course_code")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,orphanRemoval = true)
//    @JsonManagedReference
    private List<Subject> subjects = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String courseCode;

    @Column(nullable = false)
    private String courseName;

    private String description;
    private Integer durationMonths;

    @Column(name = "credit_hours")
    private Integer creditHours;

    @Column(nullable = false)
    private Boolean active = true;

    private Double courseFee;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Batch> batches = new ArrayList<>();

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}


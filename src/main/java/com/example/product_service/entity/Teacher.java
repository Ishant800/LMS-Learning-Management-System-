package com.example.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<>();

    private String qualification;
    private Integer experienceYears;
    private String specialization;

    private LocalDate joiningDate;

    private Boolean active = true;

    @PrePersist
    public void prePersist(){
        joiningDate = LocalDate.now();
    }
}

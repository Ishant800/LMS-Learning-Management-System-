package com.example.product_service.modules.tenant.entity;

import com.example.product_service.modules.auth.entity.User;
import com.example.product_service.modules.course.entity.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "tenants")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"users", "courses", "hibernateLazyInitializer", "handler"})
public class Tenant {

    @Id
    @Column(name = "tenant_id")
    private String id;

    @Column(nullable = false,unique = true)
    private String tenantName;

    @Column(name = "organization_name",nullable = false)
    private String orgName;

    @Column(name = "admin_email",nullable = false)
    private String adminEmail;

    @Column(name = "admin_password", nullable = false)
    private String adminPassword;

    private String address;
    private String contactEmail;
    private String contactPhone;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "subscription_plan")
    private String subscriptionPlan;

    @Column(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL)
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Course> courses = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}

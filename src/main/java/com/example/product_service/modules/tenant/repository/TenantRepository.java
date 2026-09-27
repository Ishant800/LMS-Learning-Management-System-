package com.example.product_service.modules.tenant.repository;

import com.example.product_service.modules.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant,String> {
    Optional<Tenant> findByAdminEmail(String adminEmail);
}




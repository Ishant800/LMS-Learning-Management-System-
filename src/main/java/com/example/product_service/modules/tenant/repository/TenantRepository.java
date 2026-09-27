package com.example.product_service.modules.tenant.repository;

import com.example.product_service.modules.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant,String> {
    Tenant findByAdminEmail(String adminEmail);
}

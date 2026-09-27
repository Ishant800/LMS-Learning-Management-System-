package com.example.product_service.modules.tenant.dto;

import com.example.product_service.modules.tenant.entity.Tenant;
import lombok.Builder;

@Builder
public record TenantResponseDto(
        String id,
        String tenantName,
        String orgName,
        String adminEmail,
        String address,
        String contactEmail,
        String contactPhone
) {
    public TenantResponseDto(Tenant tenant){
        this (
                tenant.getId(),
                tenant.getTenantName(),
                tenant.getOrgName(),
                tenant.getAdminEmail(),
                tenant.getAddress(),
                tenant.getContactEmail(),
                tenant.getContactPhone()
        );
    }
}

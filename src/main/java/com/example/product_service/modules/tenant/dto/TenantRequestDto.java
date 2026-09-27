package com.example.product_service.modules.tenant.dto;


import lombok.*;

@Builder
public record TenantRequestDto(
     String tenantName,
     String orgName,
     String adminEmail,
     String adminPassword,
     String address,
     String contactEmail,
     String contactPhone){ }

package com.example.product_service.modules.tenant.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantLoginDto {
    private String adminEmail;
    private String adminPassword;
}

package com.example.product_service.Dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantLoginDto {
    private String adminEmail;
    private String adminPassword;
}

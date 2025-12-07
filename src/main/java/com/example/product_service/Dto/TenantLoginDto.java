package com.example.product_service.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class TenantLoginDto {
    private String adminEmail;
    private String adminPassword;
}

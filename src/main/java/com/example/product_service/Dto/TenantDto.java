package com.example.product_service.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {
    private String tenantId;
    private String tenantName;
    private String organizationName;
    private String adminEmail;
    private String adminPassword;
    private String address;
    private String contactEmail;
    private String contactPhone;
    private String subscriptionPlan;

}

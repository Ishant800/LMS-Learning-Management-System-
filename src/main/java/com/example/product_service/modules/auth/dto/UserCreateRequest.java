package com.example.product_service.modules.auth.dto;


import com.example.product_service.common.enums.Role;

public class UserCreateRequest {
    private String tenantId;
    private String username;
    private String password;
    private Role role;
    private String fullName;
    private String email;
    private String phone;
}

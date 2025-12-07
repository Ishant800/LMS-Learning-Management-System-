package com.example.product_service.Dto;

import com.example.product_service.entity.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String tenantId;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullName;
    private String email;
    private String phone;
    private String profilePictureUrl;
}


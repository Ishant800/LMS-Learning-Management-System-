package com.example.product_service.Dto;

import com.example.product_service.entity.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String tenantId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String fullName;
    private String email;
    private String phone;
    private String profilePictureUrl;
}

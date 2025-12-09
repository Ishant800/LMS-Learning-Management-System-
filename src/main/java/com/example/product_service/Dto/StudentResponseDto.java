package com.example.product_service.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private String TenantId;
    private Long UserId;
    private String rollNo;
    private LocalDate dob;
    private String gender;
    private String address;
    private String guardianName;
    private String guardianPhone;


}

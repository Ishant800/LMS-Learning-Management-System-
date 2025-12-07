package com.example.product_service.service;

import com.example.product_service.Dto.UserDto;
import com.example.product_service.Dto.UserLoginDto;
import com.example.product_service.Dto.UserResponseDto;
import com.example.product_service.entity.Tenant;
import com.example.product_service.entity.User;
import com.example.product_service.repository.TenantRepository;
import com.example.product_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, TenantRepository tenantRepository) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
    }

    public UserResponseDto createUser(UserDto dto){
        Tenant tenant = tenantRepository.findById(dto.getTenantId()).orElseThrow(()-> new RuntimeException("tenant not found please contact your organization!"));

        User user = new User();
        user.setTenant(tenant);
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setUsername(dto.getUsername());
        user.setProfilePictureUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3LALal1UCNSOdzUpct6GKzQVj87E8M3UXNbWQ7183DwgETpkMzHkbI44&s");

      User saveduser =  userRepository.save(user);
      UserResponseDto userdata = new UserResponseDto();
      userdata.setUsername(saveduser.getUsername());
      userdata.setRole(saveduser.getRole());
      userdata.setPhone(saveduser.getPhone());
      userdata.setFullName(saveduser.getFullName());
      userdata.setTenantId(tenant.getId());
      userdata.setProfilePictureUrl(saveduser.getProfilePictureUrl());
      userdata.setEmail(saveduser.getEmail());

      return userdata;
    }


    public String userLogin(UserLoginDto dto){
        User user = userRepository.findByEmail(dto.getEmail());
        if(user == null) return "user not found";

        if(!encoder.matches(dto.getPassword(),user.getPassword())){
            return "password is invalid!";
        }

        return "Login successfully";
    }

}

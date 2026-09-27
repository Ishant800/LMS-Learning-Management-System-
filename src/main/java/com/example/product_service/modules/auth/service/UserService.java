package com.example.product_service.modules.auth.service;


import com.example.product_service.common.messaging.NotificationEvent;
import com.example.product_service.common.messaging.NotificationProducer;
import com.example.product_service.common.messaging.NotificationType;
import com.example.product_service.modules.auth.dto.UserDto;
import com.example.product_service.modules.auth.dto.UserLoginDto;
import com.example.product_service.modules.auth.dto.UserResponseDto;

import com.example.product_service.modules.auth.entity.User;

import com.example.product_service.common.exception.UserNotFoundException;


import com.example.product_service.modules.auth.repository.UserRepository;
import com.example.product_service.modules.tenant.entity.Tenant;
import com.example.product_service.modules.tenant.repository.TenantRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final NotificationProducer producer;
    public UserService(UserRepository userRepository, TenantRepository tenantRepository, NotificationProducer producer) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.producer = producer;
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

        Map<String,String> userData = Map.of("orgName", tenant.getOrgName(),"userName",saveduser.getFullName());
        Map<String ,String> adminData = Map.of("orgName", tenant.getOrgName() , "registrantName", saveduser.getFullName());


      producer.sendNotification(new NotificationEvent(user.getEmail(), NotificationType.USER_REGISTRATION,userData));

      producer.sendNotification(new NotificationEvent(tenant.getAdminEmail(),NotificationType.ADMIN_USER_ALERT,adminData));

      return userdata;
    }



    public String userLogin(UserLoginDto dto){
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new UserNotFoundException("user not found"));
        if(user == null) return "user not found";

        if(!encoder.matches(dto.getPassword(),user.getPassword())){
            return "password is invalid!";
        }



        return "Login successfully";
    }


    public User getUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Users not found"));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
    }

}

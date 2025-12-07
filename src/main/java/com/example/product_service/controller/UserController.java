package com.example.product_service.controller;

import com.example.product_service.Dto.UserDto;
import com.example.product_service.Dto.UserLoginDto;
import com.example.product_service.Dto.UserResponseDto;
import com.example.product_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createStaff")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }


    @PostMapping("/userLogin")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto dto){
        return ResponseEntity.ok(userService.userLogin(dto));
    }
}

package com.example.product_service.modules.auth.controller;

import com.example.product_service.modules.auth.dto.UserDto;
import com.example.product_service.modules.auth.dto.UserLoginDto;
import com.example.product_service.modules.auth.dto.UserResponseDto;
import com.example.product_service.common.security.jwt.JwtService;
import com.example.product_service.modules.auth.entity.User;
import com.example.product_service.modules.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto dto){

        Authentication authentication =
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return token;
    }
    @PostMapping("/createStaff")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }


    @PostMapping("/userLogin")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto dto){
        return ResponseEntity.ok(userService.userLogin(dto));
    }

    @GetMapping("/getuser")
    public ResponseEntity<User> getuser(@RequestBody String email){
        User user = userService.getUser(email);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getusers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

}

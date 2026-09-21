package com.example.product_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class Extracontroller {

    @GetMapping("/dashboard")
    public String dashbaord(){
        return "staff dashbaord";
    }

}

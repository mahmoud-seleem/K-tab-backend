package com.example.Backend.controller;

import com.example.Backend.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/usertype/")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @GetMapping()
    public String getUserType(HttpServletRequest request){
        return jwtService.getUserType(request);
    }
}

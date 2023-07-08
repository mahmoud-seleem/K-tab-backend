package com.example.Backend.controller;

import com.example.Backend.schema.UserInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.UserService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @GetMapping()
    public UserInfo getUserInfo(HttpServletRequest request) throws InputNotLogicallyValidException {
        String type = jwtService.getUserType(request);
        UUID userId = jwtService.getUserId(request);
        if (type.equals("ADMIN")){
            return userService.getAuthorInfo(userId);
        }else {
            return userService.getStudentInfo(userId);
        }
    }
}

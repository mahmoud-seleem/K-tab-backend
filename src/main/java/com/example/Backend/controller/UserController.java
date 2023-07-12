package com.example.Backend.controller;

import com.example.Backend.schema.UserInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.UserService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationUtils validationUtils;
    @GetMapping()
    public UserInfo getUserInfo(HttpServletRequest request,@ValidParam UUID userId) throws InputNotLogicallyValidException {
        UUID id;
        String type;
        if (userId == null){
            id = jwtService.getUserId(request);
            type = jwtService.getUserType(request);
        }else {
            type = validationUtils.checkUserIsExisted("userId",userId);
            id = userId;
        }
        if (type.equals("AUTHOR")){
            return userService.getAuthorInfo(id);
        }else {
            return userService.getStudentInfo(id);
        }
    }

}

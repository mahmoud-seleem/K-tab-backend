package com.example.Backend.controller;

import com.example.Backend.schema.SettingsForm;
import com.example.Backend.schema.SettingsResponse;
import com.example.Backend.security.JwtService;
import com.example.Backend.validation.json.ValidJson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.Backend.security.Role.ADMIN;

@RestController
@CrossOrigin
@RequestMapping("/settings/")
public class SettingsController {

    @Autowired
    private StudentSettingsController studentSettingsController;
    @Autowired
    private AuthorSettingsController authorSettingsController;

    @Autowired
    private JwtService jwtService;

    @PutMapping()
    public SettingsResponse updateUserSettings(HttpServletRequest request,@ValidJson("SettingsForm") SettingsForm form){
        String userType = jwtService.getUserType(request);
        if(userType.equals(ADMIN.name())){
            return authorSettingsController.updateAuthorSettingsInfo(
                    request,form);
        }else {
            return studentSettingsController.updateStudentSettingsInfo(
                    request, form
            );
        }
    }
    @GetMapping()
    public SettingsResponse getUserSettings(HttpServletRequest request){
        String userType = jwtService.getUserType(request);
        if(userType.equals(ADMIN.name())){
            return authorSettingsController.
                    getAuthorSettingsInfo(request);
        }else {
            return studentSettingsController.
                    getStudentSettingsInfo(request);
        }
    }
}

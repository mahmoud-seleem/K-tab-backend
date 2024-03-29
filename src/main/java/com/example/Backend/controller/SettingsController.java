package com.example.Backend.controller;

import com.example.Backend.schema.SettingsForm;
import com.example.Backend.schema.SettingsResponse;
import com.example.Backend.security.JwtService;
import com.example.Backend.validation.json.ValidJson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.Backend.security.Role.AUTHOR;

@RestController
@CrossOrigin
@RequestMapping("/settings/")
@Validated
public class SettingsController {

    @Autowired
    private StudentSettingsController studentSettingsController;
    @Autowired
    private AuthorSettingsController authorSettingsController;

    @Autowired
    private JwtService jwtService;

    @PutMapping()
    public SettingsResponse updateUserSettings(HttpServletRequest request,@Valid @ValidJson("SettingsForm") SettingsForm form){
        String userType = jwtService.getUserType(request);
        if(userType.equals(AUTHOR.name())){
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
        if(userType.equals(AUTHOR.name())){
            return authorSettingsController.
                    getAuthorSettingsInfo(request);
        }else {
            return studentSettingsController.
                    getStudentSettingsInfo(request);
        }
    }
}

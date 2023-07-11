package com.example.Backend.controller;

import com.example.Backend.schema.SettingsForm;
import com.example.Backend.schema.SettingsResponse;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.StudentSettingsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
public class StudentSettingsController {

    @Autowired
    private StudentSettingsService studentSettingsService;

    @Autowired
    private JwtService jwtService;

    public SettingsResponse updateStudentSettingsInfo(HttpServletRequest request, SettingsForm form) {
        form.setStudentId(jwtService.getUserId(request));
        SettingsResponse response = new SettingsResponse();
        try {
            response = studentSettingsService.updateStudentSettingsInfo(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public SettingsResponse getStudentSettingsInfo(HttpServletRequest request) {
        SettingsResponse response = new SettingsResponse();
        try {
            response = studentSettingsService
                    .getStudentSettingsInfo(
                            jwtService.getUserId(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}

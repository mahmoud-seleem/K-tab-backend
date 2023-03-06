package com.example.Backend.controller;

import com.example.Backend.service.StudentSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/settings")
public class StudentSettingsController {

    @Autowired
    private StudentSettingsService studentSettingsService;

}

package com.example.Backend.controller;

import com.example.Backend.schema.DisabilityInfo;
import com.example.Backend.service.DisabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/disabilities/")
public class DisabilityController {
    @Autowired
    private DisabilityService disabilityService;
    @GetMapping
    public List<DisabilityInfo> getAllDisabilities(){
        return disabilityService.getAllDisabilities();
    }
}

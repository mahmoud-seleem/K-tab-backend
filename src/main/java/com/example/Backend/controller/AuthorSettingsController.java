package com.example.Backend.controller;

import com.amazonaws.retry.internal.AuthRetryParameters;
import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.schema.SettingsForm;
import com.example.Backend.schema.SettingsResponse;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.AuthorSettingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Component
public class AuthorSettingsController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorSettingsService authorSettingsService;
//    @PostMapping()
//    public AuthorSettingsForm setAuthorSettingsInfo(@RequestBody AuthorSettingsForm form){
//        AuthorSettingsForm response = new AuthorSettingsForm();
//        try {
//            response = authorSettingsService.setAuthorSettingsInfo(form);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return response;
//    }
    public SettingsResponse updateAuthorSettingsInfo(HttpServletRequest request,SettingsForm form){
        form.setAuthorId(jwtService.getUserId(request));
        SettingsResponse response = new SettingsResponse();
        try {
            response = authorSettingsService.updateAuthorSettingsInfo(form);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    public SettingsResponse getAuthorSettingsInfo(HttpServletRequest request){
        SettingsResponse response = new SettingsResponse();
        try {
            response = authorSettingsService
                    .getAuthorSettingsInfo(
                            jwtService.getUserId(request));
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

//    public AuthorSettings setAuthorSettings(@RequestBody Map<String,Object> authorAndSettings){
//        UUID authorId = UUID.fromString((String) authorAndSettings.get("authorId"));
//        Author author = authorRepository.findById(authorId).get();
//        ObjectMapper objectMapper = new ObjectMapper();
//        AuthorSettings authorSettings = objectMapper.convertValue(authorAndSettings.get("authorSettings"),AuthorSettings.class);
//        author.setAuthorSettings(authorSettings);
//        return authorSettingsRepository.save(authorSettings);
//    }
//
//    public AuthorSettings getAuthorSettings(@PathVariable UUID authorId){
//        return authorRepository.findById(authorId).get().getAuthorSettings();
//    }
}

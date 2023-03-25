package com.example.Backend.controller;

import com.amazonaws.retry.internal.AuthRetryParameters;
import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.service.AuthorSettingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/author/settings")
public class AuthorSettingsController {

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public AuthorSettings setAuthorSettings(@RequestBody Map<String,Object>[] authorAndSettings){
        UUID authorId = UUID.fromString((String) authorAndSettings[0].get("authorId"));
        Author author = authorRepository.findById(authorId).get();
        ObjectMapper objectMapper = new ObjectMapper();
        AuthorSettings authorSettings = objectMapper.convertValue(authorAndSettings[1],AuthorSettings.class);
        author.setAuthorSettings(authorSettings);
        return authorSettingsRepository.save(authorSettings);
    }

    @GetMapping(path = "/getsettings/{authorId}")
    public AuthorSettings getAuthorSettings(@PathVariable UUID authorId){
        return authorRepository.findById(authorId).get().getAuthorSettings();
    }
}

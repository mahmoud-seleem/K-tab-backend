package com.example.Backend.service;

import com.example.Backend.Repository.AuthorSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorSettingsService {

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;
}

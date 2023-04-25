package com.example.Backend.service;

import com.example.Backend.Repository.StudentSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSettingsService {

    @Autowired
    private StudentSettingsRepository studentSettingsRepository;
}

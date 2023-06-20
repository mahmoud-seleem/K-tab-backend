package com.example.Backend.controller;

import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.InteractionRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Interaction;
import com.example.Backend.model.Student;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inter")
public class InteractionController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    }

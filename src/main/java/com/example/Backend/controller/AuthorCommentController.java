package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorCommentController {
    @Autowired
    private AuthorRepository authorRepository;



}

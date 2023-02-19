package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/new")
    public Author saveNewAuthor(){
        Author author = new Author("mahmoud");
        AuthorComment comment1 = new AuthorComment("blablabla");
        Author a = authorRepository.save(author);
        return a;
    }
    @GetMapping("/get/{id}")
    public Author getAuthor(@PathVariable UUID id){
         return authorRepository.findById(id).get();
    }


}

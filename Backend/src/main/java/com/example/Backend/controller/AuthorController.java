package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorCommentRepository;
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
    @Autowired
    private AuthorCommentRepository authorCommentRepository;
    @GetMapping("/new")
    public Author saveNewAuthor(){
        Author author = new Author("mahmoud");
        AuthorComment comment1 = new AuthorComment("blablabla");
        author.addAuthorComment(comment1);
        Author a = authorRepository.save(author);
        return a;
    }
    @GetMapping("/get/{id}")
    public List<AuthorComment> getAuthor(@PathVariable UUID id){
        return authorRepository.findById(id).get().getAuthorCommentList();
    }

    @GetMapping("/newcomment/{id}")
    public AuthorComment saveNewAuthorComment(@PathVariable UUID id){
        AuthorComment authorComment = new AuthorComment("blablablablablabalbala");
        Author a = authorRepository.findById(id).get();
        a.addAuthorComment(authorComment);
        AuthorComment ac = authorCommentRepository.save(authorComment);
        return ac;
    }

}

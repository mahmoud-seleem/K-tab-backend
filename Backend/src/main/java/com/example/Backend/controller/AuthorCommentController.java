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
import java.util.UUID;

@RestController
@RequestMapping("authorComment/")
public class AuthorCommentController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorCommentRepository authorCommentRepository;

    @GetMapping("/newComment/{id}")
    public AuthorComment saveNewAuthorComment(@PathVariable UUID id){
        AuthorComment authorComment = new AuthorComment("blablabla");
        AuthorComment ac = authorCommentRepository.save(authorComment);
        Author a = authorRepository.findById(id).get();
        ac.setAuthor(a);
        a.addAuthorComment(ac);
        return ac;
    }
}

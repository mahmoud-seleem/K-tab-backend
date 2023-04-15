package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorCommentRepository;
import com.example.Backend.Repository.AuthorNotificationRepository;
import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorComment;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;

    @Autowired
    private AuthorNotificationRepository authorNotificationRepository;
    @GetMapping("/new")
    public Author saveNewAuthor(){
        Author author = new Author("mahmoud");
        Author a = authorRepository.save(author);
        return a;
    }
    @GetMapping("/getauthor/{id}")
    public Author getAuthor(@PathVariable UUID id){
        return authorRepository.findById(id).get();
    }

    @GetMapping("/newcomment/{id}")
    public AuthorComment saveNewAuthorComment(@PathVariable UUID id){
        AuthorComment authorComment = new AuthorComment("blablablablablabalbala");
        Author a = authorRepository.findById(id).get();
        a.addAuthorComment(authorComment);
        AuthorComment ac = authorCommentRepository.save(authorComment);
        return ac;
    }

    @GetMapping("/get/all")
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @CrossOrigin
    @PostMapping
    public Author addNewAuthor(@RequestBody Author a){
        return authorRepository.save(a);
    }
    @GetMapping("/getcomments/{id}")
    public List<AuthorComment> getAllAuthorComments(@PathVariable UUID id){
        Author a = authorRepository.findById(id).get();
        return a.getAuthorCommentList();
    }
    @GetMapping("/getcommentauthor/{id}")
    public Author getCommentAuthor(@PathVariable UUID id){
        AuthorComment authorComment = authorCommentRepository.findById(id).get();
        return authorComment.getAuthor();
    }
    @GetMapping("/newsettings/{id}")
    public AuthorSettings newAuthorSettings(@PathVariable UUID id){
        Author author = authorRepository.findById(id).get();
        AuthorSettings authorSettings = new AuthorSettings();
        author.setAuthorSettings(authorSettings);
        return authorSettingsRepository.save(authorSettings);
    }
    @GetMapping("/getauthorsettings/{id}")
    public AuthorSettings getAuthorSettings(@PathVariable UUID id){
        Author a = authorRepository.findById(id).get();
        return a.getAuthorSettings();
    }
    @GetMapping("/getsettingsauthor/{id}")
    public Author getSettingsAuthor(@PathVariable UUID id){
        AuthorSettings a = authorSettingsRepository.findById(id).get();
        return a.getAuthor();
    }
}

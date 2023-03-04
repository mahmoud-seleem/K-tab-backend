package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private StudentCommentRepository studentCommentRepository;
    @Autowired
    private AuthorCommentRepository authorCommentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @GetMapping("/newchapter")
    public Chapter creatNewChapter(){
        Chapter chapter = new Chapter("chapter one");
        return  chapterRepository.save(chapter);
    }
    @GetMapping("/newstudent")
    public Student creatNewStudent(){
        Student student = new Student("mahmoud");
        return (studentRepository.save(student));
    }

    @GetMapping("/newauthor")
    public Author creatNewAuthor(){
        Author author = new Author("mohamed");
        return (authorRepository.save(author));
    }
    @GetMapping("/getallstudents")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/getallauthors")
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("/newauthorcomment/{id}")
    public AuthorComment createNewAuthorComment(@PathVariable UUID id){
        AuthorComment authorComment = new AuthorComment("blablabla");
        chapterRepository.findById(id).get().addAuthorComment(authorComment);
        authorCommentRepository.save(authorComment);
        return authorComment;
    }
    @GetMapping("/newstudentcomment/{id}")
    public StudentComment createNewStudentComment(@PathVariable UUID id){
        StudentComment studentComment = new StudentComment("blablablablabla");
        chapterRepository.findById(id).get().addStudentComment(studentComment);
        studentCommentRepository.save(studentComment);
        return studentComment;
    }
}

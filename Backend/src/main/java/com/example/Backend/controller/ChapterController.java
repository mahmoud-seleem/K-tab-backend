package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    @GetMapping("/newchapter")
    public Chapter creatNewChapter(){
        Book book = bookRepository.findByTitle("JAVA");
        Chapter chapter = new Chapter("chapter one");
        book.addChapter(chapter);
        return  chapterRepository.save(chapter);
    }
    @GetMapping("/newbook")
    public Book creatNewBook(){
        //Chapter chapter = new Chapter("chapter one");
        Book book = new Book("JAVA");
        return  bookRepository.save(book);
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

    @GetMapping("/getallchapters")
    public List<Chapter> getAllChapters() {return chapterRepository.findAll();}

    @GetMapping("/getallbooks")
    public List<Book> getAllBooks(){return bookRepository.findAll();}

    @GetMapping("/getbookchapters")
    public List<Chapter> getbookChapters(){
        return bookRepository.findByTitle("JAVA").getChapters();
    }
    @GetMapping("/getchapterbook")
    public Book getChapterBook(){
        return chapterRepository.findByTitle("chapter one").getBook();
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
        //studentRepository.findBy()
        return studentComment;
    }

}

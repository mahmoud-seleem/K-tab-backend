//package com.example.Backend.controller;
//
//
//import com.example.Backend.Repository.BookRepository;
//import com.example.Backend.Repository.RatingRepository;
//import com.example.Backend.Repository.StudentRepository;
//import com.example.Backend.model.Book;
//import com.example.Backend.model.Rating;
//import com.example.Backend.model.Student;
//import com.example.Backend.service.RatingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/rating")
//public class RatingController {
//
//    @Autowired
//    private StudentRepository studentRepository;
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private RatingRepository ratingRepository;
//    @GetMapping("/newbook")
//    public Book creatNewBook(){
//        //Chapter chapter = new Chapter("chapter one");
//        Book book = new Book("JAVA");
//        return  bookRepository.save(book);
//    }
//
//    @GetMapping("/newstudent")
//    public Student creatNewStudent(){
//        Student student = new Student("mahmoud");
//        return (studentRepository.save(student));
//    }
//    @GetMapping("/getallstudents")
//    public List<Student> getAllStudents(){
//        return studentRepository.findAll();
//    }
//    @GetMapping("/getallbooks")
//    public List<Book> getAllBooks(){return bookRepository.findAll();}
//
////    @GetMapping("/newrating")
////    public Rating createNewRating(){
////        Rating rating = new Rating(5);
////        Book book = bookRepository.findByTitle("JAVA");
////        Student student = studentRepository.findByName("mahmoud");
////        student.addRating(rating);
////        book.addRating(rating);
////        return ratingRepository.save(rating);
////    }
//
//
//}

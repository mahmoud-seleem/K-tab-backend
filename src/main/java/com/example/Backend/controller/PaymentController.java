package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/newbook")
    public Book createNewBook() {
        Book book = new Book("JAVA");
        return bookRepository.save(book);
    }
    @GetMapping("/newstudent")
    public Student creatNewStudent() {
        Student student = new Student("mahmoud");
        return (studentRepository.save(student));
    }
    @GetMapping("/getallstudents")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/getallbooks")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/newpayment")
    public Payment createNewPayment(){
        Book book = bookRepository.findByTitle("JAVA");
        Student student = studentRepository.findByName("mahmoud");
        Payment payment = new Payment("payment info");
        book.addPayment(payment);
        student.addPayment(payment);
        return paymentRepository.save(payment);
    }

    @GetMapping("/getbookpayments")
    public List<Payment> getBookPayments(){
        return bookRepository.findByTitle("JAVA").getPaymentList();
    }
    @GetMapping("/getstudentpayments")
    public List<Payment> getStudentPayments(){
        return studentRepository.findByName("mahmoud").getPaymentList();
    }
    @GetMapping("/getallpayments")
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
}

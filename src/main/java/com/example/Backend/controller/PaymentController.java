package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.PaymentInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin()
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PaymentService paymentService;
    @PostMapping("buy/")
    public PaymentInfo buyBook(@RequestParam UUID bookId,
                        HttpServletRequest request){
        return paymentService.buyBook(jwtService.getUserId(request),bookId);
    }

    @GetMapping("all-student-payments/")
    public List<PaymentInfo> getAllStudentPayments(HttpServletRequest request){
        return paymentService.getAllStudentPayments(
                jwtService.getUserId(request));
    }
    @GetMapping("all-student-payments1/")
    public List<PaymentInfo> getAllStudentPayments1(HttpServletRequest request){
        return paymentService.getAllStudentPayments1(
                jwtService.getUserId(request));
    }
    @GetMapping("library/")
    public List<BookHeader> getAllStudentPayments2(HttpServletRequest request){
        return paymentService.getStudentLibrary(
                jwtService.getUserId(request));
    }
    @GetMapping("all-book-payments/")
    public List<PaymentInfo> getAllBookPayments(@RequestParam UUID bookId){
        return paymentService.getAllBookPayments(bookId);
    }
}

package com.example.Backend.controller;

import com.example.Backend.schema.PaymentCount;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.AuthorDashboardService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Author-Dashboard/")
public class AuthorDashboard {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthorDashboardService authorDashboardService;
    @GetMapping("top3-books/")
    public List<PaymentCount> getTop3BooksWithMostPayments(HttpServletRequest request) throws InputNotLogicallyValidException {
        return authorDashboardService.getTop3BookWithMostPayments(
                jwtService.getUserId(request)
        );
    }
}

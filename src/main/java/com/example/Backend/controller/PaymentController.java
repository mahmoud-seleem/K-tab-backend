package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.FavouriteOrder;
import com.example.Backend.schema.FavouritesOrder;
import com.example.Backend.schema.PaymentInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.PaymentService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
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
    public PaymentInfo buyBook(@ValidParam UUID bookId,
                               HttpServletRequest request) throws InputNotLogicallyValidException {
        return paymentService.buyBook(jwtService.getUserId(request), bookId);
    }
    public void buyBook(@ValidParam UUID bookId,UUID studentId) throws InputNotLogicallyValidException {
        paymentService.buyBookWithRandomRating(studentId, bookId);
    }

    @GetMapping("all-student-payments/")
    public List<PaymentInfo> getAllStudentPayments(HttpServletRequest request) {
        return paymentService.getAllStudentPayments(
                jwtService.getUserId(request));
    }

    @GetMapping("all-student-payments1/")
    public List<PaymentInfo> getAllStudentPayments1(HttpServletRequest request) {
        return paymentService.getAllStudentPayments1(
                jwtService.getUserId(request));
    }

    @GetMapping("library/")
    public List<BookHeader> getAllStudentPayments2(HttpServletRequest request) {
        return paymentService.getStudentLibrary(
                jwtService.getUserId(request));
    }

    @GetMapping("all-book-payments/")
    public List<PaymentInfo> getAllBookPayments(@ValidParam UUID bookId) {
        return paymentService.getAllBookPayments(bookId);
    }

    @GetMapping("fav/")
    public List<BookHeader> getFavourites(HttpServletRequest request) {
        return paymentService.getFavourites(
                jwtService.getUserId(request)
        );
    }

    @PostMapping("add-to-fav/")
    public List<BookHeader> addToFavourites(@ValidParam UUID bookId
            , HttpServletRequest request) throws InputNotLogicallyValidException {
        return paymentService.addBookToFavourite(
                jwtService.getUserId(request),
                bookId
        );
    }

    @DeleteMapping("remove-from-fav/")
    public List<BookHeader> removeFromFavourites(@ValidParam UUID bookId
            , HttpServletRequest request) throws InputNotLogicallyValidException {
        return paymentService.removeBookFromFavourite(
                jwtService.getUserId(request),
                bookId
        );
    }

    @PutMapping("update-fav/")
    public List<BookHeader> updateFavourites(
            @ValidJson("FavouritesOrder") FavouritesOrder favouriteOrders
            , HttpServletRequest request) throws InputNotLogicallyValidException {
        return paymentService.updateFavouritesOrder(
                jwtService.getUserId(request),
                favouriteOrders.getFavouriteOrders()
        );
    }
}

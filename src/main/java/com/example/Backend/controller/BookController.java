package com.example.Backend.controller;


import com.example.Backend.Repository.BookRepository;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.BookPage;
import com.example.Backend.schema.PaymentInfo;
import com.example.Backend.schema.StudentBookInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.BookService;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.chrono.ChronoLocalDate;
import java.util.*;

@RestController
@CrossOrigin()
@RequestMapping("/book/")
public class BookController {

    @Autowired
    private BookService bookService;


    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookRepository bookdRepository;
//    @GetMapping("/{id}")
//    public Book findBookById(@PathVariable UUID id){
//        return bookService.findBookById(id);
//    }

    @PostMapping()
    public BookInfo saveNewBook(HttpServletRequest request, @ValidJson("BookInfo") BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
        return bookService.saveNewBook(bookInfo);
    }
    @PostMapping("/random/")
    public String saveNewBook(HttpServletRequest request) throws Exception {
        BookInfo bookInfo = new BookInfo();
        Random random = new Random();
        bookInfo.setTags(Arrays.asList("Math","AI"));
        bookInfo.setAuthorId(jwtService.getUserId(request));
        bookInfo.setBookAbstract("Master the math needed to excel in data science, machine learning. In this book author Thomas Nield guides you through areas like calculus, probability, linear algebra, and statistics and how they apply to techniques like neural networks.");
        for (int i = 0  ; i < 14; i++){
            bookInfo.setPrice((double) (random.nextInt(200 - 50 + 1) + 50));
            bookInfo.setBookCoverPhotoAsBinaryString(
                    bookService.downloadAndEncodeImage(i));
            bookInfo.setTitle(Utils.TITLES[i]);
            bookService.saveNewBook(bookInfo);
        }
        return "DONE";
    }

    @PutMapping()
    public BookInfo updateBookInfo(HttpServletRequest request, @ValidJson("BookInfo") BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
        return bookService.updateBookInfo(bookInfo);
    }

    @GetMapping()
    public BookInfo getBookInfo(HttpServletRequest request, @ValidParam UUID bookId) throws InputNotLogicallyValidException {
        if (jwtService.getUserType(request).equals("AUTHOR")) {
            return bookService.getBookInfo(bookId);
        } else {
            return bookService.getStudentBookInfo(jwtService.getUserId(request), bookId);
        }

    }
    @PostMapping("rating/")
    public StudentBookInfo addRatingValue(HttpServletRequest request ,
                                          @ValidParam UUID bookId,
                                          @ValidParam int rating) throws InputNotLogicallyValidException {
        return bookService.addRatingValue(
                jwtService.getUserId(request),
                bookId,rating
        );
    }

    @GetMapping("/all/")
    public List<UUID> getAllBookIds() {
        return bookService.getAllBookIds();
    }

    @GetMapping("/all-with-tagName/")
    public List<Map<String, Object>> getAllBooksWithTagName(@RequestParam String tagName) {
        return bookService.getAllBookWithTagName(tagName);
    }

    @GetMapping("/all-with-title/")
    public List<Map<String, Object>> getAllBooksWithTitle(@RequestParam String title) {
        return bookService.getAllBookWithTitle(title);
    }

    @GetMapping("/all-with-tagAndTitle/")
    public List<Map<String, Object>> getAllBooksWithTagNameAndTitle(@RequestParam String tagName,
                                                                    @RequestParam String title) {
        return bookService.getAllBookWithTitleAndTag(title, tagName);
    }

    @GetMapping("/all-with-tagOrTitle/")
    public List<Map<String, Object>> getAllBooksWithTagNameOrTitle(@RequestParam String tagName,
                                                                   @RequestParam String title) {
        return bookService.getAllBookWithTitleOrTag(title, tagName);
    }

    @GetMapping("tags/")
    public List<String> getAllBookTags(){
        return bookService.getAllBookTags();
    }
}
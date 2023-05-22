package com.example.Backend.controller;


import com.example.Backend.Repository.BookRepository;
import com.example.Backend.model.Book;
import com.example.Backend.model.Tag;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    public BookInfo saveNewBook(HttpServletRequest request, @RequestBody BookInfo bookInfo){
        bookInfo.setAuthorId(jwtService.getUserId(request));
        BookInfo response = new BookInfo();
        try {
            response = bookService.saveNewBook(bookInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    @PutMapping()
    public BookInfo updateBookInfo(HttpServletRequest request,@RequestBody BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
        BookInfo response = new BookInfo();
        try {
            response = bookService.updateBookInfo(bookInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping()
    public BookInfo getBookInfo(@RequestParam UUID bookId){
        return bookService.getBookInfo(bookId);
    }
//    @GetMapping("/add/specificBook")
//    public Book insertSpecificBook(){
//        return bookService.insertSpecificBook();
//    }

//    @GetMapping("/get/tags/{id}")
//    public List<Tag> getBookTags(@PathVariable UUID id){
//        return bookService.getBookTags(id);
//    }


//    @GetMapping("/get/all")
//    public List getAllBooks(){
//        return bookService.getAllBooks();
//    }

//    @GetMapping("get/author/books/{id}")
//    public List getAllAuthorBooks(@PathVariable UUID id){
//        return bookService.getAllAuthorBooks(id);
//    }
}

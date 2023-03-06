package com.example.Backend.controller;


import com.example.Backend.model.Book;
import com.example.Backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Book findBookById(@PathVariable UUID id){
        return bookService.findBookById(id);
    }

    @PostMapping()
    public Book insertBook(@RequestBody Book book){
        return bookService.insertBook(book);
    }

    @PutMapping
    public Book updateBookInfo(@RequestBody Book book){
        return bookService.updateBookInfo(book);
    }

    @GetMapping("/add/specificBook")
    public Book insertSpecificBook(){
        return bookService.insertSpecificBook();
    }
}

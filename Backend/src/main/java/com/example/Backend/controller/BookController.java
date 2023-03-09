package com.example.Backend.controller;


import com.example.Backend.model.Book;
import com.example.Backend.model.Tag;
import com.example.Backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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

    @GetMapping("/get/tags/{id}")
    public Set<Tag> getBookTags(@PathVariable UUID id){
        return bookService.getBookTags(id);
    }


    @GetMapping("/get/all")
    public List getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("get/author/books/{id}")
    public List getAllAuthorBooks(@PathVariable UUID id){
        return bookService.getAllAuthorBooks(id);
    }
}

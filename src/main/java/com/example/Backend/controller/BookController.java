package com.example.Backend.controller;


import com.example.Backend.Repository.BookRepository;
import com.example.Backend.model.Book;
import com.example.Backend.model.Tag;
import com.example.Backend.schema.BookInfo;
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


    @Autowired
    private BookRepository bookdRepository;
    @GetMapping("/{id}")
    public Book findBookById(@PathVariable UUID id){
        return bookService.findBookById(id);
    }

    @PostMapping()
    public BookInfo insertBook(@RequestBody BookInfo bookInfo){
        return bookService.saveNewBook(bookInfo);
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
    public List<Tag> getBookTags(@PathVariable UUID id){
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

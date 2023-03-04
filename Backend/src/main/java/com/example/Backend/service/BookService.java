package com.example.Backend.service;

import com.example.Backend.Repository.BookRepository;
import com.example.Backend.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book findBookById(UUID id){
        return bookRepository.findById(id).orElseThrow();
    }

    public Book insertBook(Book book){
        return bookRepository.save(book);
    }

    public Book updateBookInfo(Book book){
        Book currentBook = bookRepository.findById(book.getId()).orElseThrow();
        currentBook.setTitle(book.getTitle());
        currentBook.setBookAbstract(book.getBookAbstract());
        return bookRepository.save(currentBook);
    }
}

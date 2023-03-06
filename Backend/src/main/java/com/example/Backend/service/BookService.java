package com.example.Backend.service;

import com.example.Backend.Repository.BookRepository;
import com.example.Backend.model.Book;
import com.example.Backend.model.Rating;
import com.example.Backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
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
        Book currentBook = bookRepository.findById(book.getBookId()).orElseThrow();
        currentBook.setTitle(book.getTitle());
        currentBook.setBookAbstract(book.getBookAbstract());
        return bookRepository.save(currentBook);
    }

    public Book insertSpecificBook(){
        Book book = new Book("Mariam's fav book");
        Student student = new Student("Mariam");
        Rating rating = new Rating(book, student, 5);
        Set<Rating> ratingSet = new HashSet<Rating>();
        ratingSet.add(rating);
        book.setRatings(ratingSet);
        return bookRepository.save(book);
    }
}
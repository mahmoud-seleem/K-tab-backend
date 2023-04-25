package com.example.Backend.Repository;

import com.example.Backend.model.Book;
import com.example.Backend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

//    @Query(
//            value = "SELECT * FROM book WHERE book.title = ?1",
//            nativeQuery = true)
//    Book findByTitle(String title);
    @Query(
            value = "SELECT * FROM book WHERE book.title = ?1",
            nativeQuery = true)
    Book findByTitle(String title);
}

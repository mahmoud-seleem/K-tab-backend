package com.example.Backend.Repository;

import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<Chapter, UUID> {

    @Query(
            value = "SELECT * FROM chapter WHERE chapter.title = ?1",
            nativeQuery = true)
    Chapter findByTitle(String title);

    Optional<Chapter> findByChapterOrderAndBook(int chapterOrder, Book book);
}

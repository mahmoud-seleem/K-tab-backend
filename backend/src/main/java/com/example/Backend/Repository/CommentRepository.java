package com.example.Backend.Repository;

import com.example.Backend.model.Book;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByChapterOrderByDate(Chapter chapter);
    @Query(
            value = "SELECT * FROM comment WHERE comment.date > ?2 " +
                    "AND comment.chapter_id = ?1 "+
                    "ORDER BY comment.date " +
                    "LIMIT ?3",
            nativeQuery = true)
    List<Comment> getNextPage(UUID chapterId,LocalDateTime date, int limit );

    @Query(
            value = "SELECT * FROM comment WHERE comment.date < ?2 " +
                    "AND comment.chapter_id = ?1 "+
                    "ORDER BY comment.date " +
                    "LIMIT ?3",
            nativeQuery = true)
    List<Comment> getPrevPage(UUID chapterId,@Param("date") LocalDateTime date, @Param("limit") int limit);
    @Query(
            value = "SELECT * FROM comment WHERE comment.date < ?2 " +
                    "AND comment.chapter_id = ?1 "+
                    "ORDER BY comment.date DESC " +
                    "LIMIT ?3",
            nativeQuery = true)
    List<Comment> getPrevPageDesc(UUID chapterId,@Param("date") LocalDateTime date, @Param("limit") int limit);
}

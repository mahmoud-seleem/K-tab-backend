package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "author_comment")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "authorCommentId")
public class AuthorComment {

    @Id
    @GeneratedValue
    @Column(name="author_comment_id" , nullable = false)
    private UUID authorCommentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date",  nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "author_id")
   // @JsonBackReference
    private Author author;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
    public AuthorComment() {
    }

    public AuthorComment(String content) {
        this.content = content;
        this.localDateTime = LocalDateTime.now();
    }

    public AuthorComment(String content, LocalDateTime localDateTime, Author author) {
        this.content = content;
        this.localDateTime = localDateTime;
        this.author = author;
    }

    public UUID getAuthorCommentId() {
        return authorCommentId;
    }

    public void setAuthorCommentId(UUID authorCommentId) {
        this.authorCommentId = authorCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}

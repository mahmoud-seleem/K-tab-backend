package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookHeader {
    private UUID bookId;
    private String bookCoverPath;
    private String title;

    public BookHeader() {
    }

    public BookHeader(UUID bookId, String bookCoverPath, String title) {
        this.bookId = bookId;
        this.bookCoverPath = bookCoverPath;
        this.title = title;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public String getBookCoverPath() {
        return bookCoverPath;
    }

    public void setBookCoverPath(String bookCoverPath) {
        this.bookCoverPath = bookCoverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

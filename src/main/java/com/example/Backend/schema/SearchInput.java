package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SearchInput {
    private UUID authorId;
    private String title;

    private String bookCoverPath;
    public SearchInput() {
    }

    public SearchInput(UUID authorId, String title, String bookCoverPath) {
        this.authorId = authorId;
        this.title = title;
        this.bookCoverPath = bookCoverPath;
    }

    public String getBookCoverPath() {
        return bookCoverPath;
    }

    public void setBookCoverPath(String bookCoverPath) {
        this.bookCoverPath = bookCoverPath;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

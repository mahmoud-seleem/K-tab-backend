package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SearchInput {
    private UUID authorId;
    private String title;

    public SearchInput() {
    }

    public SearchInput(UUID authorId, String title) {
        this.authorId = authorId;
        this.title = title;
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

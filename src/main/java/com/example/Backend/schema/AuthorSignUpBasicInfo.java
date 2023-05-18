package com.example.Backend.schema;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AuthorSignUpBasicInfo {
    private UUID authorId;
    private String authorName;
    private String authorEmail;
    private String contact;

    public AuthorSignUpBasicInfo() {
    }

    public AuthorSignUpBasicInfo(String authorName, String authorEmail, String contact) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.contact = contact;
    }

    public AuthorSignUpBasicInfo(UUID authorId, String authorName, String authorEmail, String contact) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.contact = contact;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

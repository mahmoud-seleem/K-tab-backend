package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthorSignUpResponse {
    private UUID authorId;
    private String authorName;
    private String authorEmail;
    private String contact;
    private String profilePhotoPath;

    private UUID authorSettingsId;
    public AuthorSignUpResponse() {
    }

    public AuthorSignUpResponse(UUID authorId, String authorName, String authorEmail, String contact, String profilePhotoPath, UUID authorSettingsId) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.contact = contact;
        this.profilePhotoPath = profilePhotoPath;
        this.authorSettingsId = authorSettingsId;
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

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public UUID getAuthorSettingsId() {
        return authorSettingsId;
    }

    public void setAuthorSettingsId(UUID authorSettingsId) {
        this.authorSettingsId = authorSettingsId;
    }
}

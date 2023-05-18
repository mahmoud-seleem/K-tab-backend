package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorSignUpResponse extends AuthorSignUpBasicInfo{
    private String profilePhotoPath;

    private UUID authorSettingsId;

    private String token;
    public AuthorSignUpResponse() {
    }

    public AuthorSignUpResponse(
            UUID authorId,
            String authorName,
            String authorEmail,
            String contact,
            String profilePhotoPath,
            UUID authorSettingsId,
            String token) {
        super(authorId,authorName,authorEmail,contact);
        this.profilePhotoPath = profilePhotoPath;
        this.authorSettingsId = authorSettingsId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

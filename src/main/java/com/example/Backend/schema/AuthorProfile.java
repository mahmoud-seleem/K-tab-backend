package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorProfile extends AuthorSignUpResponse{
    private List<BookInfo> bookHeaders ;

    public List<BookInfo> getBookHeaders() {
        return bookHeaders;
    }

    public void setBookHeaders(List<BookInfo> bookHeaders) {
        this.bookHeaders = bookHeaders;
    }

    public AuthorProfile(List<BookInfo> bookHeaders) {
        this.bookHeaders = bookHeaders;
    }
    public AuthorProfile() {
    }

    public AuthorProfile(UUID authorId, String authorName, String authorEmail, String contact, String profilePhotoPath, UUID authorSettingsId, String token, List<BookInfo> bookHeaders) {
        super(authorId, authorName, authorEmail, contact, profilePhotoPath, authorSettingsId, token);
        this.bookHeaders = bookHeaders;
    }
}

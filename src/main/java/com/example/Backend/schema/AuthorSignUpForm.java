package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@Component
public class AuthorSignUpForm extends AuthorSignUpBasicInfo{

    @NotNull
    private String password;
    private String profilePhotoAsBinaryString;


    public AuthorSignUpForm() {
    }

    public AuthorSignUpForm(String authorName, String authorEmail, String password, String contact, String profilePhotoAsBinaryString) {
        super(authorName,authorEmail,contact);
        this.password = password;
        this.profilePhotoAsBinaryString = profilePhotoAsBinaryString;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhotoAsBinaryString() {
        return profilePhotoAsBinaryString;
    }

    public void setProfilePhotoAsBinaryString(String profilePhotoAsBinaryString) {
        this.profilePhotoAsBinaryString = profilePhotoAsBinaryString;
    }
}

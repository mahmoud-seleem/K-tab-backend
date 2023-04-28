package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@Component
public class AuthorSignUpForm {
    private UUID authorId;
    @NotNull
    private String authorName;
    @NotNull
    private String authorEmail;
    @NotNull
    private String password;
    private String contact;
    private String profilePhotoAsBinaryString;


    public AuthorSignUpForm() {
    }

    public AuthorSignUpForm(String authorName, String authorEmail, String password, String contact, String profilePhotoAsBinaryString) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.password = password;
        this.contact = contact;
        this.profilePhotoAsBinaryString = profilePhotoAsBinaryString;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProfilePhotoAsBinaryString() {
        return profilePhotoAsBinaryString;
    }

    public void setProfilePhotoAsBinaryString(String profilePhotoAsBinaryString) {
        this.profilePhotoAsBinaryString = profilePhotoAsBinaryString;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }
    //    public File convertImgToFile2() throws IOException {
//        File file = new File("D:\\SBME_4\\Graduation_Project\\Platform_Backend\\temp.png");
//        OutputStream outputStream = new FileOutputStream(file);
//        outputStream.write(decodeImage());
//        return file;
//    }

}

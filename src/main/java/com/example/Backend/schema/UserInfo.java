package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private UUID userId;
    private String userName;
    private String userEmail;
    private String userType;

    private String contact;
    private String profilePhotoPath;

    public UserInfo() {
    }

    public UserInfo(UUID userId, String userName, String userEmail, String userType, String contact, String profilePhotoPath) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userType = userType;
        this.contact = contact;
        this.profilePhotoPath = profilePhotoPath;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
}

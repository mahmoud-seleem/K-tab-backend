package com.example.Backend.schema;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StudentSignUpForm extends StudentSignUpBasicInfo{
    private String password;
    private String profilePhotoAsBinaryString;
    private String educationLevel;
    private List<DisabilityHeader> disabilities;

    public StudentSignUpForm() {
    }

    public StudentSignUpForm(String password, String profilePhotoAsBinaryString, String educationLevel, List<DisabilityHeader> disabilities) {
        this.password = password;
        this.profilePhotoAsBinaryString = profilePhotoAsBinaryString;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
    }

    public StudentSignUpForm(UUID studentId, String studentName, String studentEmail, String contact, String password, String profilePhotoAsBinaryString, String educationLevel, List<DisabilityHeader> disabilities) {
        super(studentId, studentName, studentEmail, contact);
        this.password = password;
        this.profilePhotoAsBinaryString = profilePhotoAsBinaryString;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
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

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public List<DisabilityHeader> getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(List<DisabilityHeader> disabilities) {
        this.disabilities = disabilities;
    }
}

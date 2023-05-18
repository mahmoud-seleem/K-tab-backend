package com.example.Backend.schema;

import java.util.List;
import java.util.UUID;

public class StudentSignUpResponse extends StudentSignUpBasicInfo{

    private UUID studentSettingsId;
    private String profilePhotoPath;
    private String educationLevel;
    private List<String> disabilities;
    private String token;


    public StudentSignUpResponse(UUID studentId, String studentName, String studentEmail, String contact, UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<String> disabilities) {
        super(studentId, studentName, studentEmail, contact);
        this.studentSettingsId = studentSettingsId;
        this.profilePhotoPath = profilePhotoPath;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
    }

    public StudentSignUpResponse(UUID studentId, String studentName, String studentEmail, String contact, UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<String> disabilities, String token) {
        super(studentId, studentName, studentEmail, contact);
        this.studentSettingsId = studentSettingsId;
        this.profilePhotoPath = profilePhotoPath;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
        this.token = token;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public List<String> getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(List<String> disabilities) {
        this.disabilities = disabilities;
    }
}

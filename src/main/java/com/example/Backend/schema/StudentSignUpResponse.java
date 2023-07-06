package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentSignUpResponse extends StudentSignUpBasicInfo{

    private UUID studentSettingsId;
    private String profilePhotoPath;
    private String educationLevel;
    private List<Map<String,Object>> disabilities;
    private String token;


    public StudentSignUpResponse(UUID studentId, String studentName, String studentEmail, String contact, UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<Map<String, Object>> disabilities, String token) {
        super(studentId, studentName, studentEmail, contact);
        this.studentSettingsId = studentSettingsId;
        this.profilePhotoPath = profilePhotoPath;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
        this.token = token;
    }

    public StudentSignUpResponse(UUID studentId, String studentName, String studentEmail, String contact, UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<Map<String, Object>> disabilities) {
        super(studentId, studentName, studentEmail, contact);
        this.studentSettingsId = studentSettingsId;
        this.profilePhotoPath = profilePhotoPath;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
    }

    public StudentSignUpResponse(UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<Map<String, Object>> disabilities, String token) {
        this.studentSettingsId = studentSettingsId;
        this.profilePhotoPath = profilePhotoPath;
        this.educationLevel = educationLevel;
        this.disabilities = disabilities;
        this.token = token;
    }

    public StudentSignUpResponse() {

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

    public UUID getStudentSettingsId() {
        return studentSettingsId;
    }

    public void setStudentSettingsId(UUID studentSettingsId) {
        this.studentSettingsId = studentSettingsId;
    }

    public List<Map<String, Object>> getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(List<Map<String, Object>> disabilities) {
        this.disabilities = disabilities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

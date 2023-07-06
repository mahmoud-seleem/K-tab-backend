package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentProfile extends StudentSignUpResponse{
    public StudentProfile(UUID studentId, String studentName, String studentEmail, String contact, UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<Map<String, Object>> disabilities, String token) {
        super(studentId, studentName, studentEmail, contact, studentSettingsId, profilePhotoPath, educationLevel, disabilities, token);
    }

    public StudentProfile(UUID studentId, String studentName, String studentEmail, String contact, UUID studentSettingsId, String profilePhotoPath, String educationLevel, List<Map<String, Object>> disabilities) {
        super(studentId, studentName, studentEmail, contact, studentSettingsId, profilePhotoPath, educationLevel, disabilities);
    }

    public StudentProfile() {
    }
}

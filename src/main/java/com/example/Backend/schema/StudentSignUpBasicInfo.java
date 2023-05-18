package com.example.Backend.schema;

import java.util.UUID;

public class StudentSignUpBasicInfo {

    private UUID studentId;
    private String studentName;
    private String studentEmail;
    private String contact;

    public StudentSignUpBasicInfo() {
    }

    public StudentSignUpBasicInfo(UUID studentId, String studentName, String studentEmail, String contact) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.contact = contact;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

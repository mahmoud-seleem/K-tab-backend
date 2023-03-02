package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","studentCommentList"})
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_email", nullable = false)
    private String studentEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_photo", nullable = false)
    private String profilePhoto;

    @Column(name = "contact", nullable = false)
    private String contact;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentComment> studentCommentList;

    public Student() {
    }

    public Student(String studentName, String studentEmail, String password, String profilePhoto, String contact) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
    }

    public Student(String studentName) {
        this.studentName = studentName;
        this.studentEmail = "mahmoudsaleem522@gmail.com";
        this.contact = "01061424231";
        this.password = "12345678";
        this.profilePhoto = "profile_photo_link";
        this.studentCommentList = new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void addStudentComment(StudentComment studentComment) {
        this.getStudentCommentList().add(studentComment);
        studentComment.setStudent(this);
    }

    public void removeStudentComment(StudentComment studentComment) {
        this.getStudentCommentList().remove(studentComment);
        studentComment.setStudent(null);
    }

    public List<StudentComment> getStudentCommentList() {
        return studentCommentList;
    }

    public void setStudentCommentList(List<StudentComment> studentCommentList) {
        this.studentCommentList = studentCommentList;
    }
}
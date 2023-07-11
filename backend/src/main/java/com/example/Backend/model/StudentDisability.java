package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class StudentDisability {

    @Id
    @GeneratedValue
    private UUID studentDisabilityId;
    private String disabilityDetails;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "disability_id")
    private Disability disability;

    public StudentDisability() {
    }

    public StudentDisability(String disabilityDetails, Student student, Disability disability) {
        this.disabilityDetails = disabilityDetails;
        this.student = student;
        this.disability = disability;
    }

    public StudentDisability(UUID studentDisabilityId, String disabilityDetails, Student student, Disability disability) {
        this.studentDisabilityId = studentDisabilityId;
        this.disabilityDetails = disabilityDetails;
        this.student = student;
        this.disability = disability;
    }

    public UUID getStudentDisabilityId() {
        return studentDisabilityId;
    }

    public void setStudentDisabilityId(UUID studentDisabilityId) {
        this.studentDisabilityId = studentDisabilityId;
    }

    public String getDisabilityDetails() {
        return disabilityDetails;
    }

    public void setDisabilityDetails(String disabilityDetails) {
        this.disabilityDetails = disabilityDetails;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Disability getDisability() {
        return disability;
    }

    public void setDisability(Disability disability) {
        this.disability = disability;
    }
}

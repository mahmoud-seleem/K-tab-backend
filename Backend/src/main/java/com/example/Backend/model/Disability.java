package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "disability")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","studentList"})
public class Disability {
    @Id
    @GeneratedValue
    @Column(name = "disability_id", nullable = false)
    private UUID disabilityId;

    @Column(name = "disability_name" ,nullable = false)
    private String disabilityName;

    @Column(name = "disability_details")
    private String disabilityDetails;

    @ManyToMany
    @JoinTable(
            name = "student_disabilities",
            joinColumns = @JoinColumn(name = "disability_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> studentList = new ArrayList<>();

    public Disability() {
    }

    public Disability(String disabilityName, String disabilityDetails) {
        this.disabilityName = disabilityName;
        this.disabilityDetails = disabilityDetails;
    }
    public Disability(String disabilityName) {
        this.disabilityName = disabilityName;
        this.disabilityDetails = "none";
    }

    public UUID getDisabilityId() {
        return disabilityId;
    }

    public void setDisabilityId(UUID disabilityId) {
        this.disabilityId = disabilityId;
    }

    public String getDisabilityName() {
        return disabilityName;
    }

    public void setDisabilityName(String disabilityName) {
        this.disabilityName = disabilityName;
    }

    public String getDisabilityDetails() {
        return disabilityDetails;
    }

    public void setDisabilityDetails(String disabilityDetails) {
        this.disabilityDetails = disabilityDetails;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
    public void addStudent(Student student){
        this.getStudentList().add(student);
    }
    public void removeStudent(Student student){
        this.getStudentList().remove(student);
    }
}

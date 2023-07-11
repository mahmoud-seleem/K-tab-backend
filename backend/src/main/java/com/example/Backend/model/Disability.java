package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "disability")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","studentDisabilityList"})
public class Disability {
    @Id
    @GeneratedValue
    @Column(name = "disability_id", nullable = false)
    private UUID disabilityId;

    @Column(name = "disability_name" ,nullable = false)
    private String disabilityName;

    @OneToMany(mappedBy = "disability")
    private List<StudentDisability> studentDisabilityList = new ArrayList<>();

    public Disability() {
    }
    public Disability(String disabilityName) {
        this.disabilityName = disabilityName;
    }

    public List<StudentDisability> getStudentDisabilityList() {
        return studentDisabilityList;
    }

    public void setStudentDisabilityList(List<StudentDisability> studentDisabilityList) {
        this.studentDisabilityList = studentDisabilityList;
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
    public void removeStudentDisability(StudentDisability studentDisability){
        this.getStudentDisabilityList().remove(studentDisability);
        studentDisability.setDisability(null);
    }
    public void addStudentDisability(StudentDisability studentDisability){
        this.getStudentDisabilityList().add(studentDisability);
        studentDisability.setDisability(this);
    }

}

package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "student_settings")
public class StudentSettings {

    @Id
    @GeneratedValue
    @Column(name = "setting_id")
    UUID settingId;
}

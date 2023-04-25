//package com.example.Backend.model;
//
//import jakarta.persistence.*;
//
//import java.util.UUID;
//
//@Table(name = "reading")
//public class Reading {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "reading_id", nullable = false)
//    private UUID readingId;
//
//    @ManyToOne
//    @JoinColumn(name = "chapter_id")
//    private Chapter chapter;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
//
//    @Column(name = "reading_progress" , nullable = false)
//    private int readingProgress;
//
//    public Reading() {
//    }
//
//    public Reading(Chapter chapter, Student student, int readingProgress) {
//        this.chapter = chapter;
//        this.student = student;
//        this.readingProgress = readingProgress;
//    }
//
//    public Reading(int readingProgress) {
//        this.readingProgress = readingProgress;
//    }
//
//    public UUID getReadingId() {
//        return readingId;
//    }
//
//    public void setReadingId(UUID readingId) {
//        this.readingId = readingId;
//    }
//
//    public Chapter getChapter() {
//        return chapter;
//    }
//
//    public void setChapter(Chapter chapter) {
//        this.chapter = chapter;
//    }
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public int getReadingProgress() {
//        return readingProgress;
//    }
//
//    public void setReadingProgress(int readingProgress) {
//        this.readingProgress = readingProgress;
//    }
//}

package com.example.Backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student_comment")
public class StudentComment {

    @Id
    @GeneratedValue
    @Column(name="student_comment_id" , nullable = false)
    private UUID studentCommentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date",  nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
    public StudentComment() {
    }


    public StudentComment(String content) {
        this.content = content;
        this.localDateTime = LocalDateTime.now();
    }

    public StudentComment(String content, LocalDateTime localDateTime, Student student) {
        this.content = content;
        this.localDateTime = localDateTime;
        this.student = student;
    }
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public UUID getstudentCommentId() {
        return studentCommentId;
    }

    public void setstudentCommentId(UUID studentCommentId) {
        this.studentCommentId = studentCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void addStudent(Student student){
        this.setStudent(student);
        List currentCommentsList = student.getStudentCommentList();
        currentCommentsList.add(this);
        student.setStudentCommentList(currentCommentsList);
    }
}

package com.example.Backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student_notification")
public class StudentNotification {

    @Id
    @GeneratedValue
    @Column(name="notification_id" , nullable = false)
    private UUID notificationId;

    @Column(name = "date",  nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "content",  nullable = false)
    private String content;

    @Column(name = "source_type",  nullable = false)
    private String sourceType;

    @Column(name = "source_id",  nullable = false)
    private UUID sourceId;

    @ManyToOne
    @JoinColumn(name = "destination_student")
    private Student destinationStudent;

    public StudentNotification() {
    }

    public StudentNotification(LocalDateTime localDateTime, String content, String sourceType, UUID sourceId, Student destinationStudent) {
        this.localDateTime = localDateTime;
        this.content = content;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.destinationStudent = destinationStudent;
    }

    public StudentNotification(LocalDateTime localDateTime, String content, String sourceType, UUID sourceId) {
        this.localDateTime = localDateTime;
        this.content = content;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
    }
    public StudentNotification(String content,UUID sourceId) {
        this.content = content;
        this.sourceId = sourceId;
        this.localDateTime = LocalDateTime.now();
        this.sourceType = "Author";
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }

    public Student getDestinationStudent() {
        return destinationStudent;
    }

    public void setDestinationStudent(Student destinationStudent) {
        this.destinationStudent = destinationStudent;
    }
}

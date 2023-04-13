package com.example.Backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author_notification")
public class AuthorNotification {

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
    @JoinColumn(name = "destination_author")
    private Author destinationAuthor;

    public AuthorNotification() {
    }

    public AuthorNotification(LocalDateTime localDateTime, String content, String sourceType, UUID sourceId, Author destinationAuthor) {
        this.localDateTime = localDateTime;
        this.content = content;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.destinationAuthor = destinationAuthor;
    }

    public AuthorNotification(LocalDateTime localDateTime, String content, String sourceType, UUID sourceId) {
        this.localDateTime = localDateTime;
        this.content = content;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
    }


    public AuthorNotification(String content,UUID sourceId) {
        this.content = content;
        this.sourceId = sourceId;
        this.localDateTime = LocalDateTime.now();
        this.sourceType = "student";
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

    public Author getDestinationAuthor() {
        return destinationAuthor;
    }

    public void setDestinationAuthor(Author destinationAuthor) {
        this.destinationAuthor = destinationAuthor;
    }
}

package com.example.Backend.model;

import com.example.Backend.schema.enums.UserType;
import com.example.Backend.security.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comment",indexes = @Index(columnList = "date"))
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id", nullable = false)
    private UUID CommentId;

    @Column(name = "commenter_type")
    private String commenterType;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "has_mentions")
    private Boolean hasMentions;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "mentioned_users")
    private List<UUID> mentionedUsers = new ArrayList<>();

    public Comment() {
    }

    public Comment(String commenterType, String content, LocalDateTime date, Boolean hasMentions) {
        this.commenterType = commenterType;
        this.content = content;
        this.date = date;
        this.hasMentions = hasMentions;
    }

    public UUID getCommentId() {
        return CommentId;
    }

    public void setCommentId(UUID commentId) {
        CommentId = commentId;
    }

    public String getCommenterType() {
        return commenterType;
    }

    public void setCommenterType(String commenterType) {
        this.commenterType = commenterType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getHasMentions() {
        return hasMentions;
    }

    public void setHasMentions(Boolean hasMentions) {
        this.hasMentions = hasMentions;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<UUID> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(List<UUID> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public void removeFromChapter(Chapter chapter) {
        chapter.getCommentList().remove(this);
        this.setChapter(null);
    }
    public void removeFromStudent(Student student) {
        student.getStudentCommentList().remove(this);
        this.setStudent(null);
    }
    public void removeFromAuthor(Author author) {
        author.getAuthorCommentList().remove(this);
        this.setAuthor(null);
    }
}

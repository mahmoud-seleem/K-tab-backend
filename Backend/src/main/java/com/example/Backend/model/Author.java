package com.example.Backend.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue
    @Column(name="author_id" , nullable = false)
    private UUID authorId;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "author_email", nullable = false)
    private String authorEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_photo", nullable = false)
    private String profilePhoto;

    @Column(name = "contact", nullable = false)
    private String contact;

    @OneToMany(mappedBy = "author")
    private List<AuthorComment> authorCommentList;

    public Author() {
    }

    public Author(String authorName) {
        this.authorName = authorName;
        this.authorEmail = "mahmoudsaleem522@gmail.com";
        this.contact = "01061424231";
        this.password = "12345678";
        this.profilePhoto = "profile_photo_link";
        this.authorCommentList = new ArrayList<>();
    }

    public Author(String authorName, String authorEmail, String password, String profilePhoto, String contact) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
    }

    public Author(String authorName, String authorEmail, String password, String profilePhoto, String contact, List<AuthorComment> authorCommentList) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
        this.authorCommentList = authorCommentList;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
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

    public List<AuthorComment> getAuthorCommentList() {
        return authorCommentList;
    }

    public void setAuthorCommentList(List<AuthorComment> authorCommentList) {
        this.authorCommentList = authorCommentList;
    }

    public void addAuthorComment(AuthorComment authorComment){
        this.getAuthorCommentList().add(authorComment);
    }
}
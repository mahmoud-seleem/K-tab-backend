package com.example.Backend.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "author")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler",
        "authorCommentList","authorNotificationList","chaptersList",
        "authorBooksList","authorSettings"})
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

    @OneToMany(mappedBy = "destinationAuthor")
    private List<AuthorNotification> authorNotificationList;

    @OneToMany(mappedBy = "author")
    private List<Writing> chaptersList;

    @OneToMany(mappedBy = "author")
    private List<Book> authorBooksList;

    @OneToOne(mappedBy = "author")
    private AuthorSettings authorSettings;

    public Author() {
    }
    public Author(String authorName) {
        this.authorName = authorName;
        this.authorEmail = "mahmoudsaleem522@gmail.com";
        this.contact = "01061424231";
        this.password = "12345678";
        this.profilePhoto = "profile_photo_link";
        this.authorCommentList = new ArrayList<>();
        this.authorNotificationList = new ArrayList<>();
        this.chaptersList = new ArrayList<>();
    }

    public Author(String authorName, String authorEmail, String password, String profilePhoto, String contact) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
        this.authorCommentList = new ArrayList<>();
        this.authorNotificationList = new ArrayList<>();
        this.chaptersList = new ArrayList<>();
        this.authorBooksList = new ArrayList<>();
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

    public List<AuthorNotification> getAuthorNotificationList() {
        return authorNotificationList;
    }

    public void setAuthorNotificationList(List<AuthorNotification> authorNotificationList) {
        this.authorNotificationList = authorNotificationList;
    }

    public List<Book> getAuthorBooksList() {
        return authorBooksList;
    }

    public void setAuthorBooksList(List<Book> authorBooksList) {
        this.authorBooksList = authorBooksList;
    }

    public List<Writing> getChaptersList() {
        return chaptersList;
    }

    public void setChaptersList(List<Writing> chaptersList) {
        this.chaptersList = chaptersList;
    }

    public void addWriting(Writing writing){
        this.getChaptersList().add(writing);
        writing.setAuthor(this);
    }
    public void removeWriting(Writing writing){
        this.getChaptersList().remove(writing);
        writing.setAuthor(null);
    }
    public void addAuthorNotification(AuthorNotification authorNotification){
        this.getAuthorNotificationList().add(authorNotification);
        authorNotification.setDestinationAuthor(this);
    }

    public void removeAuthorNotification(AuthorNotification authorNotification){
        this.getAuthorNotificationList().remove(authorNotification);
        authorNotification.setDestinationAuthor(null);
    }
    public void addAuthorComment(AuthorComment authorComment){
        this.getAuthorCommentList().add(authorComment);
        authorComment.setAuthor(this);
    }


    public void removeAuthorComment(AuthorComment authorComment){
        this.getAuthorCommentList().remove(authorComment);
        authorComment.setAuthor(null);
    }

    public AuthorSettings getAuthorSettings() {
        return authorSettings;
    }

    public void setAuthorSettings(AuthorSettings authorSettings) {
        this.authorSettings = authorSettings;
        authorSettings.setAuthor(this);
    }

    public void addAuthorBook(Book book){
        this.getAuthorBooksList().add(book);
        book.setAuthor(this);
    }
    public void removeAuthorBook(Book book){
        this.getAuthorBooksList().remove(book);
        book.setAuthor(null);
    }
}
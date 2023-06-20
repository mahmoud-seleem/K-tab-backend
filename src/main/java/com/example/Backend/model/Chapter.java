package com.example.Backend.model;

import com.example.Backend.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chapter")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler",
        "authorCommentList","studentCommentList"
        ,"authorList","tags","interactions"})
public class Chapter {

    @Id
    @GeneratedValue
    @Column(name = "chapter_id")
    private UUID chapterId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;
    @Column(name = "audio")
    private String audio;
    @Column(name = "reading_duration")
    private Double readingDuration;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "chapter")
    private List<AuthorComment> authorCommentList;

    @OneToMany(mappedBy = "chapter")
    private List<StudentComment> studentCommentList;

    @ManyToMany()
    @JoinTable(
            name = "chapter_tags",
            joinColumns = @JoinColumn(name = "chapter_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;


    @OneToMany(mappedBy = "chapter")
    private List<Reading> readingList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "chapter_order")
    private Integer chapterOrder;
    public Chapter() {
    }

    public Chapter( String title,
                    String content,
                    double readingDuration,
                    LocalDateTime creationDate,
                    LocalDateTime lastModified,
                    List<AuthorComment> authorCommentList,
                    List<StudentComment> studentCommentList) {
        this.title = title;
        this.content = content;
        this.readingDuration = readingDuration;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.authorCommentList = authorCommentList;
        this.studentCommentList = studentCommentList;
        this.tags = new ArrayList<>();
    }
    public Chapter(String title) {
        this.title = title;
        this.chapterOrder = null;
        this.authorCommentList = new ArrayList<>();
        this.studentCommentList = new ArrayList<>();
        this.tags = new ArrayList<>();
    }
    public UUID getChapterId() {
        return chapterId;
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getReadingDuration() {
        return readingDuration;
    }

    public void setReadingDuration(double readingDuration) {
        this.readingDuration = readingDuration;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public List<AuthorComment> getAuthorCommentList() {
        return authorCommentList;
    }

    public void setAuthorCommentList(List<AuthorComment> authorCommentList) {
        this.authorCommentList = authorCommentList;
    }

    public List<StudentComment> getStudentCommentList() {
        return studentCommentList;
    }

    public void setStudentCommentList(List<StudentComment> studentCommentList) {
        this.studentCommentList = studentCommentList;
    }
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }



    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setReadingDuration(Double readingDuration) {
        this.readingDuration = readingDuration;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }


    public List<Reading> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<Reading> readingList) {
        this.readingList = readingList;
    }

        public void addReading(Reading reading){
            this.getReadingList().add(reading);
            reading.setChapter(this);
        }
    public void removeReading(Reading reading){
        this.getReadingList().add(reading);
        reading.setChapter(null);
    }
    public void addTag(Tag tag){
        this.getTags().add(tag);
        tag.getChapterList().add(this);
    }
    public void removeTag(Tag tag){
        this.getTags().remove(tag);
        tag.getChapterList().remove(this);

    }
    public void addStudentComment(StudentComment studentComment){
        this.getStudentCommentList().add(studentComment);
        studentComment.setChapter(this);
    }
    public void addAuthorComment(AuthorComment authorComment){
        this.getAuthorCommentList().add(authorComment);
        authorComment.setChapter(this);
    }
    public void removeAuthorComment(AuthorComment authorComment){
        this.getAuthorCommentList().remove(authorComment);
        authorComment.setChapter(null);
    }
    public void removeStudentComment(StudentComment studentComment){
        this.getStudentCommentList().remove(studentComment);
        studentComment.setChapter(null);
    }

    public void clearTags() {
        this.tags = new ArrayList<>();
    }
    public String getCreationDateAsString() {
        return (creationDate != null ?
                creationDate.format(Utils.formatter)
                : null);
    }

    public String getLastModifiedDateAsString() {
        return (lastModified != null ?
                lastModified.format(Utils.formatter)
                : null);
    }

    public List<String> getTagsNames() {
        List<String> tagsNames = new ArrayList<>();
        for (Tag tag : getTags()) {
            tagsNames.add(tag.getTagName());
        }
        return tagsNames;
    }
}

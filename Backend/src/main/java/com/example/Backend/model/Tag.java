package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","chapterList"})
public class Tag{

    @Id
    @GeneratedValue
    @Column(name="tag_id" , nullable = false)
    private UUID tagId;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @ManyToMany()
    @JoinTable(
            name = "chapter_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "chapter_id"))
    private List<Chapter> chapterList;

    @ManyToMany()
    @JoinTable(
            name = "book_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> bookList = new HashSet<>();



    public Tag() {
    }

    public Tag(String tagName, List<Chapter> chapterList) {
        this.tagName = tagName;
        this.chapterList = chapterList;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
        this.chapterList = new ArrayList<>();
    }

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public Set<Book> getBookList() {
        return bookList;
    }

    public void setBookList(Set<Book> bookList) {
        this.bookList = bookList;
    }

    public void addChapter(Chapter chapter){
        this.getChapterList().add(chapter);
       // chapter.getTags().add(this);
    }
    public void removeChapter(Chapter chapter){
        this.getChapterList().remove(chapter);
        chapter.getTags().remove(this);
    }
}

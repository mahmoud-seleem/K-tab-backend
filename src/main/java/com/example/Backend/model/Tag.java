package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","chapterList","bookList"})
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
    private List<Chapter> chapterList = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
//    @JoinTable(
//            name = "book_tags",
//            joinColumns = @JoinColumn(name = "tag_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> bookList = new ArrayList<>();



    public Tag() {
    }

    public Tag(String tagName, List<Chapter> chapterList) {
        this.tagName = tagName;
        this.chapterList = chapterList;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
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

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void addChapter(Chapter chapter){
        this.getChapterList().add(chapter);
       // chapter.getTags().add(this);
    }
    public void removeChapter(Chapter chapter){
        this.getChapterList().remove(chapter);
        //chapter.getTags().remove(this);
    }

    public void addBook(Book book){
        this.getBookList().add(book);
        book.getTags().add(this);
    }
    public void removeBook(Book book){
        this.getBookList().remove(book);
       book.getTags().remove(this);
    }
}

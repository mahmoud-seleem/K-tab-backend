package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.*;
import com.github.reinert.jjschema.Attributes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookHeader {

    private UUID bookId;

    private UUID authorId;
    private String authorName;
    private String bookCoverPath;
    private String title;

    private List<String> tags;
    private String bookAbstract;
    private Boolean fav;
    private Double avgRating;
    private Double price;

    public BookHeader() {
    }

    public BookHeader(UUID bookId, String bookCoverPath, String title) {
        this.bookId = bookId;
        this.bookCoverPath = bookCoverPath;
        this.title = title;
    }

    public BookHeader(UUID bookId, UUID authorId, String authorName, String bookCoverPath, String title, List<String> tags, String bookAbstract, Boolean fav) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.bookCoverPath = bookCoverPath;
        this.title = title;
        this.tags = tags;
        this.bookAbstract = bookAbstract;
        this.fav = fav;
    }
    public BookHeader(UUID bookId, UUID authorId, String authorName, String bookCoverPath, String title, List<String> tags, String bookAbstract) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.bookCoverPath = bookCoverPath;
        this.title = title;
        this.tags = tags;
        this.bookAbstract = bookAbstract;
    }

    public BookHeader(UUID bookId, UUID authorId, String authorName, String bookCoverPath, String title, List<String> tags, String bookAbstract, Double avgRating, Double price) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.bookCoverPath = bookCoverPath;
        this.title = title;
        this.tags = tags;
        this.bookAbstract = bookAbstract;
        this.avgRating = avgRating;
        this.price = price;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public String getBookCoverPath() {
        return bookCoverPath;
    }

    public void setBookCoverPath(String bookCoverPath) {
        this.bookCoverPath = bookCoverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBookAbstract() {
        return bookAbstract;
    }

    public void setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
    }

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

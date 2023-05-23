package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"bookCoverPhotoAsBinaryString"}, allowGetters = false, allowSetters = true)
public class BookInfo {

    private UUID bookId;
    @NotNull
    private String title;
    private String bookCoverPhotoAsBinaryString;
    private String bookAbstract;
    private List<String> tags;
    private Double price;
    private String publishDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID authorId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bookCoverPath = null;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastEditDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double avgRate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> chaptersTitles =null;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> contributors = null;

    public BookInfo() {
    }

    public BookInfo(UUID authorId,
                    UUID bookId,
                    String title,
                    String bookCoverPhotoAsBinaryString,
                    String bookAbstract,
                    List<String> tags,
                    String bookCoverPath,
                    String publishDate,
                    String lastEditDate,
                    Double price,
                    Double avgRate,
                    List<String> chaptersTitles,
                    List<String> contributors) {
        this.authorId = authorId;
        this.bookId = bookId;
        this.title = title;
        this.bookCoverPhotoAsBinaryString = bookCoverPhotoAsBinaryString;
        this.bookAbstract = bookAbstract;
        this.tags = tags;
        this.bookCoverPath = bookCoverPath;
        this.publishDate = publishDate;
        this.lastEditDate = lastEditDate;
        this.price = price;
        this.avgRate = avgRate;
        this.chaptersTitles = chaptersTitles;
        this.contributors = contributors;
    }

    public BookInfo(UUID authorId, String title,
                    String bookCoverPhotoAsBinaryString,
                    String bookAbstract, List<String> tags,
                    String bookCoverPath, String publishDate,
                    String lastEditDate, Double price,
                    Double avgRate, List<String> chaptersTitles, List<String> contributors) {
        this.authorId = authorId;
        this.title = title;
        this.bookCoverPhotoAsBinaryString = bookCoverPhotoAsBinaryString;
        this.bookAbstract = bookAbstract;
        this.tags = tags;
        this.bookCoverPath = bookCoverPath;
        this.publishDate = publishDate;
        this.lastEditDate = lastEditDate;
        this.price = price;
        this.avgRate = avgRate;
        this.chaptersTitles = chaptersTitles;
        this.contributors = contributors;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookCoverPath() {
        return bookCoverPath;
    }

    public void setBookCoverPath(String bookCoverPath) {
        this.bookCoverPath = bookCoverPath;
    }

    public String getBookCoverPhotoAsBinaryString() {
        return bookCoverPhotoAsBinaryString;
    }

    public void setBookCoverPhotoAsBinaryString(String bookCoverPhotoAsBinaryString) {
        this.bookCoverPhotoAsBinaryString = bookCoverPhotoAsBinaryString;
    }

    public String getBookAbstract() {
        return bookAbstract;
    }

    public void setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public List<String> getChaptersTitles() {
        return chaptersTitles;
    }

    public void setChaptersTitles(List<String> chaptersTitles) {
        this.chaptersTitles = chaptersTitles;
    }

    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }
}

package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.sql.SQLTransactionRollbackException;
import java.util.List;
import java.util.UUID;

@Component
@JsonIgnoreProperties(value={ "bookCoverPhotoAsBinaryString" }, allowGetters = false,allowSetters = true)
public class BookInfo {

    private UUID authorId;
    private UUID bookId;
    private String bookTitle;
    private String bookCoverPhotoAsBinaryString;
    private String bookAbstract;
    private List<String> tags;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bookCoverPath;


    private Double price;
    public BookInfo() {
    }

    public BookInfo(UUID authorId, UUID bookId, String bookTitle, String bookCoverPhotoAsBinaryString, String bookAbstract, List<String> tags, Double price) {
        this.authorId = authorId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookCoverPhotoAsBinaryString = bookCoverPhotoAsBinaryString;
        this.bookAbstract = bookAbstract;
        this.tags = tags;
        this.price = price;
    }

    public BookInfo(UUID authorId, String bookTitle, String bookCoverPhotoAsBinaryString, String bookAbstract, List<String> tags, Double price) {
        this.authorId = authorId;
        this.bookTitle = bookTitle;
        this.bookCoverPhotoAsBinaryString = bookCoverPhotoAsBinaryString;
        this.bookAbstract = bookAbstract;
        this.tags = tags;
        this.price = price;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
}

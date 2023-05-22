package com.example.Backend.schema;

import com.example.Backend.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChapterInfo {

    private UUID ownerId;

    private UUID chapterId;

    private UUID bookId;

    private String title;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String contentPath;

    private Double readingDuration;

    private Integer chapterOrder;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastModified;

    private List<String> tags;

    public ChapterInfo() {
    }

    public ChapterInfo(
            UUID ownerId,
            UUID chapterId,
            UUID bookId,
            String title,
            String contentPath,
            Double readingDuration,
            Integer chapterOrder,
            String creationDate,
            String lastModified,
            List<String> tags) {
        this.ownerId = ownerId;
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.title = title;
        this.contentPath = contentPath;
        this.readingDuration = readingDuration;
        this.chapterOrder = chapterOrder;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.tags = tags;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getChapterId() {
        return chapterId;
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId;
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

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public Double getReadingDuration() {
        return readingDuration;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

package com.example.Backend.schema;

import com.example.Backend.model.*;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Component
public class ChapterInfo {

    private String ownerId;
    private String chapterId;
    private String bookId;
    private String title;
    private List<String> tags;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer chapterOrder;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double readingDuration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String contentPath;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String audioPath;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String creationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastModified;

    public ChapterInfo() {
    }

    public ChapterInfo(
            UUID ownerId,
            UUID chapterId,
            UUID bookId,
            String title,
            String contentPath,
            String audioPath,
            Double readingDuration,
            Integer chapterOrder,
            String creationDate,
            String lastModified,
            List<String> tags) {
        this.ownerId = ownerId.toString();
        this.chapterId = chapterId.toString();
        this.bookId = bookId.toString();
        this.title = title;
        this.contentPath = contentPath;
        this.audioPath = audioPath;
        this.readingDuration = readingDuration;
        this.chapterOrder = chapterOrder;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.tags = tags;
    }

    public UUID getOwnerId() {
        if (ownerId == null){
            return null;
        }
        return UUID.fromString(ownerId);
    }

    public void setOwnerId(String ownerId) throws InputNotLogicallyValidException {
        if(ownerId != null){
            ValidationUtils validationUtils = new ValidationUtils();
            validationUtils.checkForValidUUIDString("ownerId",ownerId);
        }
        this.ownerId = ownerId;
    }
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId.toString();
    }

    public UUID getChapterId() {
        if (chapterId == null){
            return null;
        }
        return UUID.fromString(chapterId);
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId.toString();
    }
    public void setChapterId(String chapterId) throws InputNotLogicallyValidException {
        if(chapterId != null){
            ValidationUtils validationUtils = new ValidationUtils();
            validationUtils.checkForValidUUIDString("chapterId",chapterId);
        }
        this.chapterId = chapterId;
    }

    public UUID getBookId() {
        if (bookId == null){
            return null;
        }
        return UUID.fromString(bookId);
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId.toString();
    }
    public void setBookId(String bookId) throws InputNotLogicallyValidException {
        ValidationUtils validationUtils = new ValidationUtils();
        validationUtils.checkForValidUUIDString("bookId",bookId);
        this.bookId = bookId;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
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

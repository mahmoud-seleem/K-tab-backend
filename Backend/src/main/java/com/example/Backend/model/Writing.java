package com.example.Backend.model;

import com.example.Backend.compositeKeys.WritingKey;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "writing")
public class Writing {
    @EmbeddedId
    WritingKey writingId;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @MapsId("chapterId")
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public Writing() {
    }

    public Writing(WritingKey writingId, Author author, Chapter chapter, LocalDateTime updateDate) {
        this.writingId = writingId;
        this.author = author;
        this.chapter = chapter;
        this.updateDate = updateDate;
    }

    public WritingKey getWritingId() {
        return writingId;
    }

    public void setWritingId(WritingKey writingId) {
        this.writingId = writingId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}

package com.example.Backend.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class WritingKey implements Serializable {
    @Column(name = "author_id")
    private UUID authorId;
    @Column(name = "chapter_id")
    private UUID chapterId;

    public WritingKey() {
    }

    public WritingKey(UUID authorId, UUID chapterId) {
        this.authorId = authorId;
        this.chapterId = chapterId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getChapterId() {
        return chapterId;
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId;
    }
}

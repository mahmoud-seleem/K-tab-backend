package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChapterHeader {
    private UUID chapterId;
    private String chapterTitle;
    private Integer chapterOrder;

    public ChapterHeader() {
    }

    public ChapterHeader(UUID chapterId, String chapterTitle, Integer chapterOrder) {
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
        this.chapterOrder = chapterOrder;
    }

    public UUID getChapterId() {
        return chapterId;
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }
}

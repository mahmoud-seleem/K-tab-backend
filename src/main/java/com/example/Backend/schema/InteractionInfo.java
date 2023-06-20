package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class InteractionInfo {
    private UUID studentId;
    private UUID chapterId;
    private UUID interactionId;
    private Map<String,Object> interactionData = null;
    private Integer readingProgress = null;

    public InteractionInfo(UUID studentId, UUID chapterId, UUID interactionId, Map<String, Object> interactionData, Integer readingProgress) {
        this.studentId = studentId;
        this.chapterId = chapterId;
        this.interactionId = interactionId;
        this.interactionData = interactionData;
        this.readingProgress = readingProgress;
    }

    public InteractionInfo() {
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getChapterId() {
        return chapterId;
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId;
    }

    public UUID getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(UUID interactionId) {
        this.interactionId = interactionId;
    }

    public Integer getReadingProgress() {
        return readingProgress;
    }

    public void setReadingProgress(Integer readingProgress) {
        this.readingProgress = readingProgress;
    }

    public Map<String, Object> getInteractionData() {
        return interactionData;
    }

    public void setInteractionData(Map<String, Object> interactionData) {
        this.interactionData = interactionData;
    }
}

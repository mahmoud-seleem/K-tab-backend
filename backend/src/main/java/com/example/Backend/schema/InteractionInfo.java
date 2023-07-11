package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class InteractionInfo {
    private UUID studentId;
    private UUID chapterId;

    private UUID readingId;
    private UUID interactionId;

    private Map<String,Object> interactionData = null;

    public InteractionInfo(UUID studentId, UUID chapterId,UUID readingId, UUID interactionId, Map<String, Object> interactionData) {
        this.studentId = studentId;
        this.chapterId = chapterId;
        this.readingId = readingId;
        this.interactionId = interactionId;
        this.interactionData = interactionData;
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


    public Map<String, Object> getInteractionData() {
        return interactionData;
    }

    public UUID getReadingId() {
        return readingId;
    }

    public void setReadingId(UUID readingId) {
        this.readingId = readingId;
    }

    public void setInteractionData(Map<String, Object> interactionData) {
        this.interactionData = interactionData;
    }
}

package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class InteractionInfo {
    private String studentId;
    private String chapterId;
    private String interactionId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID readingId;
    private Map<String,Object> interactionData = null;

    public InteractionInfo(UUID studentId, UUID chapterId,UUID readingId, UUID interactionId, Map<String, Object> interactionData) {
        this.studentId = studentId.toString();
        this.chapterId = chapterId.toString();
        this.readingId = readingId;
        this.interactionId = interactionId.toString();
        this.interactionData = interactionData;
    }

    public InteractionInfo() {
    }

    public UUID getStudentId() {
        return convertStringToUUID(studentId);
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId.toString();
    }
    public void setStudentId(String studentId) throws InputNotLogicallyValidException {
        validateUUIDString("studentId",studentId);
        this.studentId = studentId;
    }

    public UUID getChapterId() {
        return convertStringToUUID(chapterId);
    }

    public void setChapterId(UUID chapterId) {
        this.chapterId = chapterId.toString();
    }
    public void setChapterId(String chapterId) throws InputNotLogicallyValidException {
        validateUUIDString("chapterId",chapterId);
        this.chapterId = chapterId;
    }

    public UUID getInteractionId() {
        return convertStringToUUID(interactionId);
    }

    public void setInteractionId(UUID interactionId) {
        this.interactionId = interactionId.toString();
    }
    public void setInteractionId(String interactionId) throws InputNotLogicallyValidException {
        validateUUIDString("interactionId",interactionId);
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
    private UUID convertStringToUUID(String s){
        if (s == null){
            return null;
        }
        return UUID.fromString(s);
    }
    private void validateUUIDString(String name,String value) throws InputNotLogicallyValidException {
        if(name != null){
            ValidationUtils validationUtils = new ValidationUtils();
            validationUtils.checkForValidUUIDString(name,value);
        }
    }
}

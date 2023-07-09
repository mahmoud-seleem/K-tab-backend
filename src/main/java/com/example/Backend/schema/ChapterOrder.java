package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChapterOrder {
    private String chapterId;
    private int order;

    public ChapterOrder(UUID chapterId, int order) {
        this.chapterId = (chapterId == null) ? null: chapterId.toString();
        this.order = order;
    }

    public ChapterOrder() {
    }

    public UUID getChapterId() {
        return convertStringToUUID(chapterId);
    }

    public void setChapterId(String chapterId) throws InputNotLogicallyValidException {
        validateUUIDString("chapterId",chapterId);
        this.chapterId = chapterId;
    }
    public void setChapterId(UUID chapterId) {
        this.chapterId = (chapterId == null) ? null: chapterId.toString();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import lombok.Data;

import java.util.List;
import java.util.UUID;

public class ChapterOrders {
    private String bookId;
    private List<ChapterOrder> chapterOrders;

    public ChapterOrders(UUID bookId, List<ChapterOrder> chapterOrders) {
        this.bookId = (bookId == null) ? null: bookId.toString();
        this.chapterOrders = chapterOrders;
    }

    public ChapterOrders() {
    }

    public List<ChapterOrder> getChapterOrders() {
        return chapterOrders;
    }

    public void setChapterOrders(List<ChapterOrder> chapterOrders) {
        this.chapterOrders = chapterOrders;
    }


    public UUID getBookId() {
        return convertStringToUUID(bookId);
    }

    public void setBookId(String bookId) throws InputNotLogicallyValidException {
        validateUUIDString("bookId",bookId);
        this.bookId = bookId;
    }
    public void setBookId(UUID bookId){
        this.bookId = bookId.toString();
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

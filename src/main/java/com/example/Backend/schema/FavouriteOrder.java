package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FavouriteOrder {
    private String bookId;
    private int order;

    public FavouriteOrder(UUID bookId, int order) {
        this.bookId = bookId.toString();
        this.order = order;
    }

    public FavouriteOrder() {
    }

    public UUID getBookId() {
        return convertStringToUUID(bookId);
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId.toString();
    }
    public void setBookId(String bookId) throws InputNotLogicallyValidException {
        validateUUIDString("bookId",bookId);
        this.bookId = bookId;
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

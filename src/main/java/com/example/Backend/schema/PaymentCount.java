package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentCount implements Comparable<PaymentCount> {
    private String bookId;
    private int numberOfStudents;

    public PaymentCount(String bookId, int numberOfStudents) {
        this.bookId = bookId;
        this.numberOfStudents = numberOfStudents;
    }

    public PaymentCount() {
    }

    public UUID getBookId() {
        return convertStringToUUID(bookId);
    }

    public void setBookId(UUID bookId) {
        this.bookId = (bookId == null) ? null : bookId.toString();
    }
    public void setBookId(String bookId) throws InputNotLogicallyValidException {
        validateUUIDString("bookId",bookId);
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
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

    @Override
    public int compareTo(PaymentCount o) {
        if (this.getNumberOfStudents() > o.getNumberOfStudents()){
            return 1;
        }else if (this.getNumberOfStudents() < o.getNumberOfStudents()){
            return -1;
        }else return 0;
    }
}

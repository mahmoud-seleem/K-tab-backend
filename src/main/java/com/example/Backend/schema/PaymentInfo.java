package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentInfo {
    private UUID paymentId;
    private String bookTitle;
    private UUID bookId;
    private String studentEmail;
    private UUID studentId;
    private String paymentInfo;
    private String recentOpenedDate;
    private int ratingValue;

    public PaymentInfo(UUID paymentId, String bookTitle, UUID bookId, String studentEmail, UUID studentId, String paymentInfo, String recentOpenedDate, int ratingValue) {
        this.paymentId = paymentId;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.studentEmail = studentEmail;
        this.studentId = studentId;
        this.paymentInfo = paymentInfo;
        this.recentOpenedDate = recentOpenedDate;
        this.ratingValue = ratingValue;
    }

    public PaymentInfo() {
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getRecentOpenedDate() {
        return recentOpenedDate;
    }

    public void setRecentOpenedDate(String recentOpenedDate) {
        this.recentOpenedDate = recentOpenedDate;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}

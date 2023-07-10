package com.example.Backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "payment",
        indexes = @Index(columnList = "recent_opened_date"))
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id",nullable = false)
    private UUID paymentId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "payment_info")
    private String paymentInfo;

    @Column(name = "recent_opened_date")
    private LocalDateTime recentOpenedDate;

    @Column(name = "recent_opened_chapter_id")
    private UUID recentOpenedChapterId ;

    @Column(name = "rating_value")
    private Integer ratingValue;
    public Payment() {
    }

    // time axis - y book or books -> payment count.
    // books - y views -> views count today.
    // categorize the student of some author. // DONE
    // top 3 books in terms of number of payment of students // DONE
    public Payment(UUID paymentId, Book book, Student student, String paymentInfo, LocalDateTime recentOpenedDate, Integer ratingValue) {
        this.paymentId = paymentId;
        this.book = book;
        this.student = student;
        this.paymentInfo = paymentInfo;
        this.recentOpenedDate = recentOpenedDate;
        this.ratingValue = ratingValue;
    }

    public Payment(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public LocalDateTime getRecentOpenedDate() {
        return recentOpenedDate;
    }

    public void setRecentOpenedDate(LocalDateTime recentOpenedDate) {
        this.recentOpenedDate = recentOpenedDate;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public UUID getRecentOpenedChapterId() {
        return recentOpenedChapterId;
    }

    public void setRecentOpenedChapterId(UUID recentOpenedChapterId) {
        this.recentOpenedChapterId = recentOpenedChapterId;
    }
}

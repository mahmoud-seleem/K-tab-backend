package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "payment")
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

    @Column(name = "payment_info",nullable = false)
    private String paymentInfo;

    public Payment() {
    }

    public Payment(Book book, Student student, String paymentInfo) {
        this.book = book;
        this.student = student;
        this.paymentInfo = paymentInfo;
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
}

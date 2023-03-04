package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue
    @Column(name = "rating_id", nullable = false)
    private UUID id;

    @Column(name = "rating_value", nullable = false)
    private int ratingValue;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
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
}

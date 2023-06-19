package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "favourite")
public class Favourite {
    @Id
    @GeneratedValue
    @Column(name = "favourite_id",nullable = false)
    private UUID favouriteId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "order")
    private int order;

    public Favourite(UUID favouriteId, Book book, Student student, int order) {
        this.favouriteId = favouriteId;
        this.book = book;
        this.student = student;
        this.order = order;
    }

    public Favourite() {
    }

    public UUID getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(UUID favouriteId) {
        this.favouriteId = favouriteId;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

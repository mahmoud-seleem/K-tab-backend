package com.example.Backend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;


@Embeddable
public class RatingKey implements Serializable {

    @JoinColumn(name = "book_id")
    private UUID bookID;

    @JoinColumn(name = "student_id")
    private UUID studentID;

    public RatingKey(UUID bookID, UUID studentID) {
        this.bookID = bookID;
        this.studentID = studentID;
    }
}

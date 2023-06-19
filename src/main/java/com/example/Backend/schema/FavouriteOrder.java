package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FavouriteOrder {
    private UUID bookId;
    private int order;

    public FavouriteOrder(UUID bookId, int order) {
        this.bookId = bookId;
        this.order = order;
    }

    public FavouriteOrder() {
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

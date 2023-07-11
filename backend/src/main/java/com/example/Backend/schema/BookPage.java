package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BookPage {
    private UUID next;
    private UUID prev;
    private List<BookHeader> bookHeaders = new ArrayList<>();

    public BookPage() {
    }


    public BookPage(UUID next, UUID prev, List<BookHeader> bookHeaders) {
        this.next = next;
        this.prev = prev;
        this.bookHeaders = bookHeaders;
    }

    public UUID getNext() {
        return next;
    }

    public void setNext(UUID next) {
        this.next = next;
    }

    public UUID getPrev() {
        return prev;
    }

    public void setPrev(UUID prev) {
        this.prev = prev;
    }

    public List<BookHeader> getBookHeaders() {
        return bookHeaders;
    }

    public void setBookHeaders(List<BookHeader> bookHeaders) {
        this.bookHeaders = bookHeaders;
    }
}

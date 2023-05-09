package com.example.Backend.security;

public enum Permissions {
    CHAPTER_WRITE("chapter_write")
    ,CHAPTER_READ("chapter_read");

    private  String name;

    Permissions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

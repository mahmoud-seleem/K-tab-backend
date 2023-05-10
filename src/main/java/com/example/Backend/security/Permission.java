package com.example.Backend.security;

public enum Permission {
    CHAPTER_WRITE("ROLE_chapter_write")
    ,CHAPTER_READ("ROLE_chapter_read");

    private  String name;

    Permission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

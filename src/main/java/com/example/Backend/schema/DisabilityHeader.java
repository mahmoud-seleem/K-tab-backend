package com.example.Backend.schema;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component

public class DisabilityHeader {
    private String name;
    private String details;

    public DisabilityHeader(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public DisabilityHeader() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

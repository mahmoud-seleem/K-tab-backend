package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvalidInfo {

    private List<String> invalidImagesPaths;

    public List<String> getInvalidImagesPaths() {
        return invalidImagesPaths;
    }

    public void setInvalidImagesPaths(List<String> invalidImagesPaths) {
        this.invalidImagesPaths = invalidImagesPaths;
    }

    public InvalidInfo(List<String> invalidImagesPaths) {
        this.invalidImagesPaths = invalidImagesPaths;
    }

    public InvalidInfo() {
    }
}

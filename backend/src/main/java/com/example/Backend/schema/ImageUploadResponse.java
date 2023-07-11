package com.example.Backend.schema;

import org.springframework.stereotype.Component;

@Component
public class ImageUploadResponse {
    private String imagePath;
    private String imageUrl;

    public ImageUploadResponse(String imagePath, String imageUrl) {
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
    }

    public ImageUploadResponse() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

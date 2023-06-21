package com.example.Backend.schema;

import com.example.Backend.schema.enums.ImageType;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ImageDescriptionDto {
    private ImageType type;
    private String content;
}

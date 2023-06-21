package com.example.Backend.schema;

import com.example.Backend.schema.enums.ImageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ImageDescriptionDto {
    private ImageType type;
    private String content;
}

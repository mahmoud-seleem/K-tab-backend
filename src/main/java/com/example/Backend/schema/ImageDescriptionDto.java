package com.example.Backend.schema;

import com.example.Backend.enums.ImageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Component
public class ImageDescriptionDto {
    private ImageType type;
    private String content;
}

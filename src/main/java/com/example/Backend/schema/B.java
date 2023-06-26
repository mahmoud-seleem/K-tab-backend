package com.example.Backend.schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class B {
    @NotBlank
    private String name;
}

package com.example.Backend.schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class A {
    @NotBlank
    private String name;
    @NotNull
    @Valid
    private B b;
}

package com.example.unittesttutorial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

    @NotBlank(message = "code can not be blank")
    private String code;
    @Min(value = 1, message = "amount must be greater then zero")
    private int amount;
}

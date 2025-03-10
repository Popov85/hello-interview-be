package com.hello.interview.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record PhoneRequest(
        Long id,
        @NotBlank String brand,
        @NotBlank String model,
        String description,
        Map<String, Object> specification
) {}

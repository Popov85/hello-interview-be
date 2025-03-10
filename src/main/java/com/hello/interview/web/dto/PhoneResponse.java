package com.hello.interview.web.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record PhoneResponse(Long id, String brand, String model, String description, Map<String, Object> specification) {
}

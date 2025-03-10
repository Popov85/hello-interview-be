package com.hello.interview.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Phone {
    private Long id;
    private String brand;
    private String model;
    private String description;
    private Map<String, Object> specification;
}

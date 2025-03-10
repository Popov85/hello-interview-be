package com.hello.interview.web.repository;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.Map;

@Entity
@Table(name = "phones", schema = "interview")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String description;
    @Convert(converter = MapConverter.class) // Apply custom converter
    @Column(columnDefinition = "jsonb") // Define the column type explicitly
    @ColumnTransformer(write = "?::jsonb")
    private Map<String, Object> specification;
}

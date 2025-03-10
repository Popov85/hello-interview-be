package com.hello.interview.web.mapper;

import com.hello.interview.web.dto.Phone;
import com.hello.interview.web.dto.PhoneRequest;
import com.hello.interview.web.dto.PhoneResponse;
import com.hello.interview.web.repository.PhoneEntity;
import org.springframework.stereotype.Component;

@Component
public class PhonesMapper {

    public PhoneResponse toResponse(Phone phone) {
        return PhoneResponse.builder()
                .id(phone.getId())
                .model(phone.getModel())
                .brand(phone.getBrand())
                .description(phone.getDescription())
                .specification(phone.getSpecification())
                .build();
    }

    public PhoneEntity toEntity(Phone phone) {
        return PhoneEntity.builder()
                .id(phone.getId())
                .model(phone.getModel())
                .brand(phone.getBrand())
                .description(phone.getDescription())
                .specification(phone.getSpecification())
                .build();
    }

    public Phone toDomain(PhoneEntity phone) {
        return Phone.builder()
                .id(phone.getId())
                .model(phone.getModel())
                .brand(phone.getBrand())
                .description(phone.getDescription())
                .specification(phone.getSpecification())
                .build();
    }

    public Phone toDomain(PhoneRequest phone) {
        return Phone.builder()
                .id(phone.id())
                .model(phone.model())
                .brand(phone.brand())
                .description(phone.description())
                .specification(phone.specification())
                .build();
    }
}

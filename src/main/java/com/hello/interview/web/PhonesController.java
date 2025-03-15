package com.hello.interview.web;

import com.hello.interview.web.dto.Phone;
import com.hello.interview.web.dto.PhoneRequest;
import com.hello.interview.web.dto.PhoneResponse;
import com.hello.interview.web.mapper.PhonesMapper;
import com.hello.interview.web.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/phones")
@PreAuthorize("hasRole('USER')")
public class PhonesController {

    private final PhoneService phoneService;

    private final PhonesMapper phonesMapper;

    // 📌 1. Get a paginated list of phones
    @GetMapping
    public Page<PhoneResponse> getPhones(Pageable pageable) {
        log.debug("Getting a page of phones= {}", pageable);
        Page<Phone> phones = phoneService.getPhones(pageable);
        return phones.map(phone ->phonesMapper.toResponse(phone));
    }

    // 📌 2. Get a phone by ID
    @GetMapping("/{id}")
    public ResponseEntity<PhoneResponse> getPhoneById(@PathVariable Long id) {
        log.debug("Get a phone by ID = {}", id);
        return ResponseEntity.ok(phonesMapper.toResponse(phoneService.getPhoneById(id)));
    }

    // 📌 3. Save a new phone
    @PostMapping
    public ResponseEntity<PhoneResponse> savePhone(@RequestBody PhoneRequest request) {
        log.debug("Saved a phone = {}", request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(phonesMapper.toResponse(phoneService.savePhone(phonesMapper.toDomain(request))));
    }

    // 📌 5. Delete a phone by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        log.debug("Deleting a phone by ID = {}", id);
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }
}

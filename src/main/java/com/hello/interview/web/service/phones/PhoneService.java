package com.hello.interview.web.service.phones;

import com.hello.interview.web.dto.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneService {
    // 📌 Get paginated list of phones
    Page<Phone> getPhones(Pageable pageable);

    // 📌 Get a phone by ID
    Phone getPhoneById(Long id);

    // 📌 Create a new phone
    Phone savePhone(Phone request);

    // 📌 Delete a phone by ID
    void deletePhone(Long id);
}

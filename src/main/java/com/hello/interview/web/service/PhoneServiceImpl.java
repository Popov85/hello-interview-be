package com.hello.interview.web.service;

import com.hello.interview.web.dto.Phone;
import com.hello.interview.web.mapper.PhonesMapper;
import com.hello.interview.web.repository.PhonesRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhonesRepository phonesRepository;

    private final PhonesMapper phonesMapper;

    @Override
    public Page<Phone> getPhones(@NonNull Pageable pageable) {
        return phonesRepository.findAll(pageable).map(phone->phonesMapper.toDomain(phone));
    }

    @Override
    public Phone getPhoneById(@NonNull Long id) {
        return phonesMapper.toDomain(phonesRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Phone not found")));
    }

    @Override
    public Phone savePhone(@NonNull Phone phone) {
        return phonesMapper.toDomain(phonesRepository.save(phonesMapper.toEntity(phone)));
    }

    @Override
    public void deletePhone(@NonNull Long id) {
        phonesRepository.deleteById(id);
    }
}

package com.prularia.magazijn.inkomendeLevering;

import org.springframework.stereotype.Service;

@Service
public class InkomendeLeveringService {

    private final InkomendeLeveringRepository inkomendeLeveringRepository;

    public InkomendeLeveringService(InkomendeLeveringRepository inkomendeLeveringRepository) {
        this.inkomendeLeveringRepository = inkomendeLeveringRepository;
    }

    public long createInkomendeLevering(InkomendeLevering inkomendeLevering) {
        return inkomendeLeveringRepository.createInkomendeLevering(inkomendeLevering);
    }

}

package com.prularia.magazijn.inkomendeLevering;

import org.springframework.stereotype.Service;

@Service
public class InkomendeLeveringsLijnService {

    private final InkomendeLeveringsLijnRepository inkomendeLeveringsLijnRepository;

    public InkomendeLeveringsLijnService(InkomendeLeveringsLijnRepository inkomendeLeveringsLijnRepository) {
        this.inkomendeLeveringsLijnRepository = inkomendeLeveringsLijnRepository;
    }

    public int createInkomendeLeveringsLijn(InkomendeLeveringsLijn inkomendeLeverings) {
        return inkomendeLeveringsLijnRepository.createInkomendeLeveringsLijn(inkomendeLeverings);
    }
}

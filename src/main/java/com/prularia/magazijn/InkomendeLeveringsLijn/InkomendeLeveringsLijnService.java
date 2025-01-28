package com.prularia.magazijn.InkomendeLeveringsLijn;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class InkomendeLeveringsLijnService {
    private final InkomendeLeveringsLijnRepository inkomendeLeveringsLijnRepository;

    public InkomendeLeveringsLijnService(InkomendeLeveringsLijnRepository inkomendeLeveringsLijnRepository) {
        this.inkomendeLeveringsLijnRepository = inkomendeLeveringsLijnRepository;
    }
    public int createInkomendeLeveringsLijn(InkomendeLeveringsLijn inkomendeLeverings) {
        return inkomendeLeveringsLijnRepository.createInkomendeLeveringsLijn(inkomendeLeverings);
    }

    public List<inkomendeLeveringsLijnDTO>  getLeveringslijnenSortedByMagazijnplaatsId() {
        return inkomendeLeveringsLijnRepository.getLeveringslijnenSortedByMagazijnplaatsId();
    }
}

package com.prularia.magazijn.InkomendeLeveringslijn;


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


    public List<InkomendeLeveringslijnDTO>  getLeveringslijnenSortedByMagazijnplaatsId() {
        return inkomendeLeveringsLijnRepository.getLeveringslijnenSortedByMagazijnplaatsId();
    }
}

package com.prularia.magazijn.bestelling;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BestellingService {

    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }


    public Bestelling findBestellingById(long bestelId) {
        return bestellingRepository.findBestellingById(bestelId)
                .orElseThrow(() -> new BestellingNietGevondenException(bestelId));
    }
}

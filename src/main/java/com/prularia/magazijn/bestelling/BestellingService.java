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

    @Transactional
    void rondBestellingAf(long bestelId) {
        bestellingRepository.rondBestellingAf(bestelId);
        // TODO: Magazijn plaatsen aanpassen

        // TODO: Stock aanpassen

        // TODO: Testen :)
    }
}

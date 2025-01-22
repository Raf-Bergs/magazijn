package com.prularia.magazijn.bestelling;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BestellingService {

    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }


    public Optional<Long> findBestelling() {
        return bestellingRepository.findBestelling();
    }

    @Transactional
    public void updateStatusToKlaarmaken(long bestelId) {
        bestellingRepository.updateStatusToKlaarmaken(bestelId);
    }


}

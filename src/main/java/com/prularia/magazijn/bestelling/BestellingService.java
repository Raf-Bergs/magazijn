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


    public Bestelling findBestellingById(long bestelId) {
        return bestellingRepository.findBestellingById(bestelId)
                .orElseThrow(() -> new BestellingNietGevondenException(bestelId));
    }

    public Optional<Bestelling> findNextBestelling() {
        return bestellingRepository.findNextBestelling();
    }

    @Transactional
    public void markAsCompleted(long bestelId) {
        bestellingRepository.updateStatus(bestelId, BestellingsStatus.ONDERWEG.getStatusId());
    }
}

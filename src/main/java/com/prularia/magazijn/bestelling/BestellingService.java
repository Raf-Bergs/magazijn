package com.prularia.magazijn.bestelling;

import com.prularia.magazijn.artikel.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;
    private final ArtikelRepository artikelRepository;

    public BestellingService(BestellingRepository bestellingRepository, ArtikelRepository artikelRepository) {
        this.bestellingRepository = bestellingRepository;
        this.artikelRepository = artikelRepository;
    }

    @Transactional
    void rondBestellingAf(AfgerondeBestellingDTO bestelling) {
        bestellingRepository.rondBestellingAf(bestelling.bestelId());
        // TODO: Magazijn plaatsen aanpassen

        // TODO: Stock aanpassen
        for (var bestellijn : bestelling.bestellijnenDTOs()) {
            artikelRepository.pasStockAan(bestellijn.artikelId(), bestellijn.aantal());
        }
        // TODO: Testen :)
    }
}

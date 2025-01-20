package com.prularia.magazijn.bestelling;

import com.prularia.magazijn.artikel.ArtikelRepository;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;
    private final ArtikelRepository artikelRepository;
    private final MagazijnplaatsRepository magazijnplaatsRepository;

    public BestellingService(BestellingRepository bestellingRepository, ArtikelRepository artikelRepository, MagazijnplaatsRepository magazijnplaatsRepository) {
        this.bestellingRepository = bestellingRepository;
        this.artikelRepository = artikelRepository;
        this.magazijnplaatsRepository = magazijnplaatsRepository;
    }

    @Transactional
    void rondBestellingAf(AfgerondeBestellingDTO bestelling) {
        // Bestel status updaten
        bestellingRepository.rondBestellingAf(bestelling.bestelId());
        for (var bestellijn : bestelling.bestellijnenDTOs()) {
            // Magazijn plaatsen aanpassen
            magazijnplaatsRepository.pasMagazijnplaatsAan(bestellijn.magazijnplaatsId(), bestellijn.aantal());
            // Stock aanpassen
            artikelRepository.pasStockAan(bestellijn.artikelId(), bestellijn.aantal());
        }
        // TODO: Testen :)
    }
}

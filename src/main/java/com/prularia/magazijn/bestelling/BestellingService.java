package com.prularia.magazijn.bestelling;

import com.prularia.magazijn.artikel.ArtikelRepository;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsNietGevondenException;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsRepository;
import com.prularia.magazijn.uitgaandeLevering.UitgaandeLevering;
import com.prularia.magazijn.uitgaandeLevering.UitgaandeLeveringsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;
    private final ArtikelRepository artikelRepository;
    private final MagazijnplaatsRepository magazijnplaatsRepository;
    private final UitgaandeLeveringsRepository uitgaandeLeveringsRepository;

    public BestellingService(BestellingRepository bestellingRepository, ArtikelRepository artikelRepository, MagazijnplaatsRepository magazijnplaatsRepository, UitgaandeLeveringsRepository uitgaandeLeveringsRepository) {
        this.bestellingRepository = bestellingRepository;
        this.artikelRepository = artikelRepository;
        this.magazijnplaatsRepository = magazijnplaatsRepository;
        this.uitgaandeLeveringsRepository = uitgaandeLeveringsRepository;
    }


    public Optional<Long> findBestelling() {
        return bestellingRepository.findBestelling();
    }

    @Transactional
    public void updateStatusToKlaarmaken(long bestelId) {
        bestellingRepository.updateStatusToKlaarmaken(bestelId);
    }

    @Transactional
    void rondBestellingAf(AfgerondeBestellingDTO bestelling) {
        // Uitgaande levering aanmaken
        var klantId = bestellingRepository.findKlantId(bestelling.bestelId());
        uitgaandeLeveringsRepository.createUitgaandeLevering(new UitgaandeLevering(0, bestelling.bestelId(), LocalDate.now(), klantId, 1));
        for (var bestellijn : bestelling.bestellijnenDTOs()) {
            // Magazijn plaatsen aanpassen
            var rij = bestellijn.magazijnPlaats().substring(0, 1);
            var rek = Integer.parseInt(bestellijn.magazijnPlaats().substring(1));
            var magazijnPlaatsId = magazijnplaatsRepository.findIdByPlaats(rij, rek).orElseThrow(() -> new MagazijnplaatsNietGevondenException(rij, rek));
            magazijnplaatsRepository.pasMagazijnplaatsAan(magazijnPlaatsId, bestellijn.aantal());
            // Stock aanpassen
            artikelRepository.pasStockAan(bestellijn.artikelId(), bestellijn.aantal());
        }
        // TODO: Testen :)
    }

    @Transactional
    public Optional<Long> findBestellingByBestelId(long bestelId) {
        return bestellingRepository.findBestellingByBestelId(bestelId);
    }
}

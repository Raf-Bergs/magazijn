package com.prularia.magazijn.inkomendeLevering;

import com.prularia.magazijn.artikel.ArtikelRepository;
import com.prularia.magazijn.magazijnplaats.Magazijnplaats;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InkomendeLeveringService {
    private final MagazijnplaatsRepository magazijnplaatsRepository;
    private final ArtikelRepository artikelRepository;

    public InkomendeLeveringService(MagazijnplaatsRepository magazijnplaatsRepository, ArtikelRepository artikelRepository) {
        this.magazijnplaatsRepository = magazijnplaatsRepository;
        this.artikelRepository = artikelRepository;
    }

    public List<InkomendeLeveringslijnDTO> verwerkInkomendeLevering(long id) {
        // TODO: Lees de levering als een List<InkomendeLeveringslijnDTO> of zo...
        var inkomendeLeveringslijnen = leesInkomendeLeveringslijnen();
        for (var inkomendeLeveringslijn : inkomendeLeveringslijnen) {
            // Vind voor elke leveringslijn (artikel) alle mogelijke plaatsen
            var plaatsen = magazijnplaatsRepository.findPlaatsenByArtikelId(inkomendeLeveringslijn.artikelId());
            // TODO: Check voor elk artikel of er genoeg ruimte is in de plaatsen waar ze al staan
            // TODO: Gebruik de repo functie van Anthony denk ik
            var maxAantal = artikelRepository.vindMax(inkomendeLeveringslijn.artikelId());
            var aantalPlaatsenBeschikbaar = plaatsen.stream().mapToInt(plaats -> maxAantal - plaats.getAantal()).sum();
            if (aantalPlaatsenBeschikbaar >= inkomendeLeveringslijn.aantalGoedgekeurd()) {
                // TODO: Maak alle mogelijke combinaties per artikel
                var combinaties = combinatiesPerArtikel(plaatsen);
            } else {
                // TODO: Als niet genoeg: nieuwe plaatsen voorzien
                // creeer nieuw entry...
            }
        }
        // TODO: Maak alle combinaties van verschillende opties voor de verschillende artikels
        // TODO: Sorteer elke combo alfabetisch/volgens ophalen
        // TODO: Selecteer het beste pad
    }

    private List<Magazijnplaats> combinatiesPerArtikel(List<Magazijnplaats> plaatsen) {

    }

    // Testfunctie
    private List<InkomendeLeveringslijnDTO> leesInkomendeLeveringslijnen() {
        var lijnen = new ArrayList<InkomendeLeveringslijnDTO>();
        lijnen.add(new InkomendeLeveringslijnDTO(1, "test1", "", 5, 0, null));
        lijnen.add(new InkomendeLeveringslijnDTO(2, "test2", "", 4, 0, null));
        lijnen.add(new InkomendeLeveringslijnDTO(3, "test3", "", 10, 0, null));
        return lijnen;
    }
}

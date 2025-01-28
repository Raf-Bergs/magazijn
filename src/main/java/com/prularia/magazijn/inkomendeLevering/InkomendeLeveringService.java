package com.prularia.magazijn.inkomendeLevering;

import com.prularia.magazijn.artikel.ArtikelRepository;
import com.prularia.magazijn.magazijnplaats.Magazijnplaats;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

            // Check voor elk artikel of er genoeg ruimte is in de plaatsen waar ze al staan
            var aantalPlaatsenBeschikbaar = aantalBeschikbaar(plaatsen);
            while (aantalBeschikbaar(plaatsen) < inkomendeLeveringslijn.aantalGoedgekeurd()) {
                // TODO: Als niet genoeg: nieuwe plaatsen voorzien
                // creeer nieuwe magazijnplaats entry...

            }
            // Maak alle mogelijke combinaties per artikel
            var alleCombinaties = combinatiesPerArtikel(plaatsen);
            // Enkel combos houden die genoeg plaats hebben
            var combinaties = alleCombinaties.stream().filter(combo -> aantalBeschikbaar(combo) >= inkomendeLeveringslijn.aantalGoedgekeurd());
        }
        // TODO: Maak alle combinaties van verschillende opties voor de verschillende artikels
        combinaties.add();
        // TODO: Sorteer elke combo alfabetisch/volgens ophalen
        // TODO: Selecteer het beste pad
        return inkomendeLeveringslijnen;
    }

    public List<List<Magazijnplaats>> combinatiesPerArtikel(List<Magazijnplaats> plaatsen) {
        var result = new LinkedList<List<Magazijnplaats>>();
        if (plaatsen.size() == 1) {
            result.add(new LinkedList<>());
            result.add(plaatsen);
            return result;
        }
        var plaats = plaatsen.removeFirst();
        var combinaties = combinatiesPerArtikel(plaatsen);
        for (var combinatie : combinaties) {
            result.add(combinatie);
            // Optimalisatie nog mogelijk: check of er al genoeg plaats is
            var uitgebreideCombinatie = new LinkedList<>(combinatie);
            uitgebreideCombinatie.add(plaats);
            result.add(uitgebreideCombinatie);
        }
        return result;
    }

    private int aantalBeschikbaar(List<Magazijnplaats> plaatsen) {
        var maxAantal = artikelRepository.vindMaxAantalInMagazijnPLaats(plaatsen.getFirst().getArtikelId());
        return plaatsen.stream().mapToInt(plaats -> (int) (maxAantal - plaats.getAantal())).sum();
    }

    private void createMagazijnplaats(long artikelId) {
        
    }

    // Testfunctie
    private List<InkomendeLeveringslijnDTO> leesInkomendeLeveringslijnen() {
        var lijnen = new ArrayList<InkomendeLeveringslijnDTO>();
        lijnen.add(new InkomendeLeveringslijnDTO(0, 1, "test1", "", 5, 0, 0, null));
        lijnen.add(new InkomendeLeveringslijnDTO(0, 2, "test2", "", 4, 0, 0, null));
        lijnen.add(new InkomendeLeveringslijnDTO(0, 3, "test3", "", 10, 0, 0, null));
        return lijnen;
    }
}

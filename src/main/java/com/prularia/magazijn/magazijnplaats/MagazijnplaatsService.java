package com.prularia.magazijn.magazijnplaats;

import com.prularia.magazijn.artikel.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MagazijnplaatsService {
    private final MagazijnplaatsRepository magazijnplaatsRepository;
    private final ArtikelRepository artikelRepository;


    public MagazijnplaatsService(MagazijnplaatsRepository magazijnplaatsRepository, ArtikelRepository artikelRepository) {
        this.magazijnplaatsRepository = magazijnplaatsRepository;
        this.artikelRepository = artikelRepository;
    }

    @Transactional
    public void aanvullenInRek(AanvullenInRekDTO dto) {
        // Controleer dat aantal positief is
        if (dto.aantal() <= 0) {
            throw new IllegalArgumentException("Aantal moet groter zijn dan 0.");
        }

        // Haal het huidige aantal op de magazijnplaats op
        int huidigAantal = magazijnplaatsRepository.getHuidigAantalOpMagazijnplaats(dto.magazijnplaatsId());

        // Haal het maximale aantal in de magazijnplaats op
        int maxAantal = magazijnplaatsRepository.getMaxAantalInMagazijnplaats(dto.artikelId());

        // Controleer of het aantal niet boven de limiet uitkomt
        if (huidigAantal + dto.aantal() > maxAantal) {
            throw new IllegalArgumentException("De som van het huidig aantal en toegevoegd aantal overschrijdt de limiet van de magazijnplaats.");

        }

        // Voorraad aanvullen in artikelen
        artikelRepository.voorraadAanvullen(dto.artikelId(), dto.aantal());

        // Specifieke magazijnplaats aanvullen
        magazijnplaatsRepository.aanvullenMagazijnplaats(dto.magazijnplaatsId(), dto.aantal());
    }
}

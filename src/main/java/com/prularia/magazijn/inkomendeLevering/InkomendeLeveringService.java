package com.prularia.magazijn.inkomendeLevering;

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

    public InkomendeLeveringService(MagazijnplaatsRepository magazijnplaatsRepository) {
        this.magazijnplaatsRepository = magazijnplaatsRepository;
    }

    public void verwerkInkomendeLevering(InkomendeLeveringDTO inkomendeLevering) {

    }

    public List<InkomendeLeveringslijnDTO> vindMagazijnPlaatsen(List<InkomendeLeveringslijnDTO> inkomendeLeveringslijnen) {
        var lijstMetPlaatsen = new ArrayList<ArrayList<Magazijnplaats>>();
        for (var inkomendeLeveringsLijn : inkomendeLeveringslijnen) {
            var plaatsen = magazijnplaatsRepository.findPlaatsenByArtikelId(inkomendeLeveringsLijn.artikelId());
            lijstMetPlaatsen.add((ArrayList<Magazijnplaats>) plaatsen);
        }
        return inkomendeLeveringslijnen;
    }
}

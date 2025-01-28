package com.prularia.magazijn.inkomendeLevering;

import com.prularia.magazijn.artikel.ArtikelRepository;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InkomendeLeveringService {

    private final InkomendeLeveringRepository inkomendeLeveringRepository;
    private final MagazijnplaatsRepository magazijnplaatsRepository;
    private final ArtikelRepository artikelRepository;

    public InkomendeLeveringService(InkomendeLeveringRepository inkomendeLeveringRepository, MagazijnplaatsRepository magazijnplaatsRepository, ArtikelRepository artikelRepository) {
        this.inkomendeLeveringRepository = inkomendeLeveringRepository;
        this.magazijnplaatsRepository = magazijnplaatsRepository;
        this.artikelRepository = artikelRepository;
    }
    @Transactional
    public long createInkomendeLevering(InkomendeLevering inkomendeLevering) {
        return inkomendeLeveringRepository.createInkomendeLevering(inkomendeLevering);
    }

}


package com.prularia.magazijn.bestelling;

import com.prularia.magazijn.pickingLocatie.PickingLocatie;
import com.prularia.magazijn.pickingLocatie.PickingLocatieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("bestelling")
public class BestellijnController {
    private final BestellijnService bestellijnService;
    private final PickingLocatieService pickingLocatieService;

    public BestellijnController(BestellijnService bestellijnService, PickingLocatieService pickingLocatieService) {
        this.bestellijnService = bestellijnService;
        this.pickingLocatieService = pickingLocatieService;
    }

    @GetMapping("{id}")
    public Stream<BestellijnDTO> getBestellijnen(@PathVariable long id) {
        var pickingPath = pickingLocatieService.getOptimizedPickingPath(id);
        return pickingPath.stream().map(this::bestellijnDTOVanPickingLocatie);
    }

    private BestellijnDTO bestellijnDTOVanPickingLocatie(PickingLocatie locatie) {
        return new BestellijnDTO(locatie.getArtikelId(), locatie.getArtikelNaam(), "",
                locatie.getAantalBesteld(), "" + locatie.getRij() + locatie.getRek());
    }

}
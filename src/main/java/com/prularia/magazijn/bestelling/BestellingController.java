package com.prularia.magazijn.bestelling;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bestellingen")
public class BestellingController {
    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @PostMapping("{id}")
    public void rondBestellingAf(@PathVariable long id, @RequestBody List<AfgerondeBestellijnDTO> bestellijnen) {
        bestellingService.rondBestellingAf(new AfgerondeBestellingDTO(id, bestellijnen));
    }

    @GetMapping("/findBestelling")
    public long findNextBestelling() {
        long bestelId = bestellingService.findBestelling()
                .orElseThrow(BestellingNietGevondenException::new);
        bestellingService.updateStatusToKlaarmaken(bestelId); // Zet op 'Klaarmaken'
        return bestelId;
    }
}

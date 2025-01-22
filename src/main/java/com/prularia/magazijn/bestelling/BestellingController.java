package com.prularia.magazijn.bestelling;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bestellingen")
public class BestellingController {

    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }


    @GetMapping("/findBestelling")
    public long findNextBestelling() {
        long bestelId = bestellingService.findBestelling()
                .orElseThrow(BestellingNietGevondenException::new);
        bestellingService.updateStatusToKlaarmaken(bestelId); // Zet op 'Klaarmaken'
        return bestelId;
    }


}

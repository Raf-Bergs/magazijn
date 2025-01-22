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
        return bestellingService.findBestelling().orElseThrow(BestellingNietGevondenException::new);
    }

    @PostMapping("/{bestelId}/voltooi")
    public void updateStatus(@PathVariable long bestelId) {
        bestellingService.updateStatus(bestelId); // Update status naar 'Onderweg'
    }
}

package com.prularia.magazijn.bestelling;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bestellingen")
public class BestellingController {

    private final BestellingService bestellingService;


    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @GetMapping("/{bestelId}")
    public Bestelling findBestellingById(@PathVariable long bestelId) {
        return bestellingService.findBestellingById(bestelId);

    }
}

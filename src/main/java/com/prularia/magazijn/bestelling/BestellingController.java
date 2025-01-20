package com.prularia.magazijn.bestelling;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bestelling")
public class BestellingController {
    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @PostMapping("{id}")
    public void rondBestellingAf(@PathVariable long id, @RequestBody List<AfgerondeBestellijnenDTO> bestellijnen) {
        bestellingService.rondBestellingAf(new AfgerondeBestellingDTO(id, bestellijnen));
    }
}

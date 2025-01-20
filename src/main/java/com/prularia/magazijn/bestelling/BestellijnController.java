package com.prularia.magazijn.bestelling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bestelling")
public class BestellijnController {
    private final BestellijnService bestellijnService;

    public BestellijnController(BestellijnService bestellijnService) {
        this.bestellijnService = bestellijnService;
    }

    @GetMapping("{id}")
    public List<BestellijnDTO> getBestellijnen(@PathVariable long id) {
        return bestellijnService.findByBestelId(id);
    }
}
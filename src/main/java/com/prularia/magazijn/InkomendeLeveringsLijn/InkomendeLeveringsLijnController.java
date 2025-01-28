package com.prularia.magazijn.InkomendeLeveringsLijn;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("inkomendeleveringslijnen")
@RestController
public class InkomendeLeveringsLijnController {
    private final InkomendeLeveringsLijnService inkomendeLeveringsLijnService;

    public InkomendeLeveringsLijnController(InkomendeLeveringsLijnService inkomendeLeveringsLijnService) {
        this.inkomendeLeveringsLijnService = inkomendeLeveringsLijnService;
    }
    @GetMapping
    public List<InkomendeLeveringsLijnDTO> getInkomendeLeveringslijn() {
        return inkomendeLeveringsLijnService.getLeveringslijnenSortedByMagazijnplaatsId();
    }
}

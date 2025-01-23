package com.prularia.magazijn.magazijnplaats;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/magazijnplaats")
public class MagazijnplaatsController {
    private final MagazijnplaatsService magazijnplaatsService;

    public MagazijnplaatsController(MagazijnplaatsService magazijnplaatsService) {
        this.magazijnplaatsService = magazijnplaatsService;
    }

    @PostMapping("/aanvullenInRek")
    public String verwerkLevering(@RequestBody @Valid AanvullenInRekDTO dto) {
        try {
            magazijnplaatsService.aanvullenInRek(dto);
            return "Levering succesvol verwerkt!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}

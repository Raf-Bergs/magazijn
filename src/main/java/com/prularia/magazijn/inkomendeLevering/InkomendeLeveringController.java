package com.prularia.magazijn.inkomendeLevering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("leveringen")
public class InkomendeLeveringController {
    private final InkomendeLeveringService inkomendeLeveringService;

    public InkomendeLeveringController(InkomendeLeveringService inkomendeLeveringService) {
        this.inkomendeLeveringService = inkomendeLeveringService;
    }

    @GetMapping
    public void leveringen() {
        System.out.println(inkomendeLeveringService.vindMagazijnPlaatsen(maakInkomendeLeveringslijnen()));
    }

    private List<InkomendeLeveringslijnDTO> maakInkomendeLeveringslijnen() {
        var lijnen = new ArrayList<InkomendeLeveringslijnDTO>();
        lijnen.add(new InkomendeLeveringslijnDTO(1, "test1", "", 5, 0, null));
        lijnen.add(new InkomendeLeveringslijnDTO(2, "test2", "", 4, 0, null));
        lijnen.add(new InkomendeLeveringslijnDTO(3, "test3", "", 10, 0, null));
        return lijnen;
    }
}

package com.prularia.magazijn.inkomendeLevering;

import com.prularia.magazijn.InkomendeLeveringsLijn.inkomendeLeveringsLijnDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class InkomendeLeveringController {
    private final long ontvangerId = 4L;  //magazijniers Id
    private final InkomendeLeveringService inkomendeLeveringService;

    public InkomendeLeveringController(InkomendeLeveringService inkomendeLeveringService) {
        this.inkomendeLeveringService = inkomendeLeveringService;
    }

    @PostMapping("/PostLevering")
    public void methodenaam(@RequestBody long leveranciersId, String leveringsbonNummer, LocalDate leveringsbondatum, LocalDate leverDatum, List<inkomendeLeveringsLijnDTO> inkomendeLeveringslijnDTOList) {
        long inkomendeLeveringsId = inkomendeLeveringService.createInkomendeLevering(new InkomendeLevering(0L, leveranciersId, leveringsbonNummer, leveringsbondatum, leverDatum, ontvangerId));
        //TODO: DTO DOORGEVEN AAN M303: sorting algorithm
    }

}

package com.prularia.magazijn.inkomendeLevering;

import java.time.LocalDate;
import java.util.List;

public record InkomendeLeveringDTO(
        long inkomendeLeveringsId,
        long leveranciersId,
        String leveringsbonNummer,
        LocalDate leveringsbondatum,
        LocalDate leverdatum,
        long ontvangerPersoneelslidId,
        List<InkomendeLeveringslijnDTO> inkomendeLeveringslijnen
) {
}

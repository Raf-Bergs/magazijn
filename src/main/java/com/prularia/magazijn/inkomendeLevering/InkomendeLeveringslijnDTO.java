package com.prularia.magazijn.inkomendeLevering;

public record InkomendeLeveringslijnDTO(
        long artikelId,
        String naam,
        String beschrijving,
        long aantalGoedgekeurd,
        long magazijnPlaatsId,
        String plaats
) {
}

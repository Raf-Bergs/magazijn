package com.prularia.magazijn.inkomendeLevering;

public record InkomendeLeveringslijnDTO(
        long leveringId,
        long artikelId,
        String naam,
        String beschrijving,
        long aantalGoedgekeurd,
        long aantalAfgekeurd,
        long magazijnPlaatsId,
        String plaats
) {
}

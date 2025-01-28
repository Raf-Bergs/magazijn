package com.prularia.magazijn.InkomendeLeveringsLijn;

public record InkomendeLeveringsLijnDTO(
        long inkomendeLeveringsId,
        long artikelId,
        String naam,
        String beschrijving,
        long aantalGoedgekeurd,
        long aantalTeruggestuurd,
        long magazijnPlaatsId,
        String plaats
) {
}
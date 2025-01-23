package com.prularia.magazijn.InkomendeLeveringsLijn;

public record InkomendeLeveringslijnDTO(
        long inkomendeLeveringsId,
        long artikelId,
        long aantalGoedgekeurd,
        long aantalTeruggestuurd,
        long magazijnPlaatsId,
        String plaats
) {}
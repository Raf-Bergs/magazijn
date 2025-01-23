package com.prularia.magazijn.InkomendeLeveringslijn;

public record InkomendeLeveringslijnDTO(
        long inkomendeLeveringsId,
        long artikelId,
        long aantalGoedgekeurd,
        long aantalTeruggestuurd,
        long magazijnPlaatsId,
        String plaats
) {}
package com.prularia.magazijn.InkomendeLeveringsLijn;

public record inkomendeLeveringsLijnDTO(
        long inkomendeLeveringsId,
        long artikelId,
        long aantalGoedgekeurd,
        long aantalTeruggestuurd,
        long magazijnPlaatsId
) {
}
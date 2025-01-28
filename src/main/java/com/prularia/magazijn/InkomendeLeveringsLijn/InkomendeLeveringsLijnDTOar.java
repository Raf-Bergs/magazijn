package com.prularia.magazijn.InkomendeLeveringsLijn;

    public record InkomendeLeveringsLijnDTOar(
            long inkomendeLeveringsId,
            long artikelId,
            long aantalGoedgekeurd,
            long aantalTeruggestuurd,
            long magazijnPlaatsId
    ) {
    }


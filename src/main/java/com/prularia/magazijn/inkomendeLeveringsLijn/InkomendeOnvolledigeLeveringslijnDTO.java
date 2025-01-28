package com.prularia.magazijn.inkomendeLeveringsLijn;

public record InkomendeOnvolledigeLeveringslijnDTO(
        long leveringId,
        long artikelId,
        long aantalGoedgekeurd,
        long aantalAfgekeurd
) {
}

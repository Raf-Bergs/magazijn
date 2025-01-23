package com.prularia.magazijn.inkomendeLevering;

public class InkomendeLeveringsLijn {

private final long inkomendeLeveringsId;
private final long artikelId;
private final long aantalGoedgekeurd;
private final long magazijnPlaatsId;

    public InkomendeLeveringsLijn(long inkomendeLeveringsId, long artikelId, long aantalGoedgekeurd, long magazijnPlaatsId) {
        this.inkomendeLeveringsId = inkomendeLeveringsId;
        this.artikelId = artikelId;
        this.aantalGoedgekeurd = aantalGoedgekeurd;
        this.magazijnPlaatsId = magazijnPlaatsId;
    }


}

package com.prularia.magazijn.bestelling;

public class Bestellijn {
    private final long bestellijnId;
    private final long bestelId;
    private final long artikelId;
    private final long aantalBesteld;
    private final long aantalGeannuleerd;

    public Bestellijn(long bestellijnId, long bestelId, long artikelId, long aantalBesteld, long aantalGeannuleerd) {
        this.bestellijnId = bestellijnId;
        this.bestelId = bestelId;
        this.artikelId = artikelId;
        this.aantalBesteld = aantalBesteld;
        this.aantalGeannuleerd = aantalGeannuleerd;
    }

    public long getBestellijnId() {
        return bestellijnId;
    }

    public long getBestelId() {
        return bestelId;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public long getAantalBesteld() {
        return aantalBesteld;
    }

    public long getAantalGeannuleerd() {
        return aantalGeannuleerd;
    }
}

package com.prularia.magazijn.artikel;

public class OnvoldoendeVoorraadException extends RuntimeException {
    public OnvoldoendeVoorraadException(long artikelId, long voorraad) {
        super(artikelId + ": " + voorraad);
    }
}

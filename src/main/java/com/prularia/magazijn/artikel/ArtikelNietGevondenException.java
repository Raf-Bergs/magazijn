package com.prularia.magazijn.artikel;

public class ArtikelNietGevondenException extends RuntimeException {
    public ArtikelNietGevondenException(long artikelId) {
        super(Long.toString(artikelId));
    }
}

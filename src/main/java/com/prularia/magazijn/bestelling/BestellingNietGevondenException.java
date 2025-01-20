package com.prularia.magazijn.bestelling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BestellingNietGevondenException extends RuntimeException {
    private final long bestelId;

    public BestellingNietGevondenException(long bestelId) {
        super("Bestelling niet gevonden voor ID: " + bestelId);
        this.bestelId = bestelId;
    }

    public long getBestelId() {
        return bestelId;
    }

}
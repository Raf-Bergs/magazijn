package com.prularia.magazijn.bestelling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BestellingNietGevondenException extends RuntimeException {
    public BestellingNietGevondenException(long bestellingId) {
        super("Bestelling niet gevonden: " + bestellingId);
    }
}

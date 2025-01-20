package com.prularia.magazijn.magazijnplaats;

public class MagazijnplaatsNietGevondenException extends RuntimeException {
    public MagazijnplaatsNietGevondenException(long magazijnplaatsId) {
        super(Long.toString(magazijnplaatsId));
    }
}

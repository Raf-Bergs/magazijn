package com.prularia.magazijn.inkomendeLevering;

import java.time.LocalDateTime;

public class InkomendeLevering {
    private final long inkomendeLeveringId;
    private final long leveringId;
    private final String leveringsbonNummer;
    private final LocalDateTime leveringsbondatum;
    private final LocalDateTime leverDatum;
    private final long ontvangerPersoneelslidId;

    public InkomendeLevering(long inkomendeLeveringId, long leveringId, String leveringsbonNummer, LocalDateTime leveringsbondatum, LocalDateTime leverDatum, long ontvangerPersoneelslidId) {
        this.inkomendeLeveringId = inkomendeLeveringId;
        this.leveringId = leveringId;
        this.leveringsbonNummer = leveringsbonNummer;
        this.leveringsbondatum = leveringsbondatum;
        this.leverDatum = leverDatum;
        this.ontvangerPersoneelslidId = ontvangerPersoneelslidId;
    }

    public long getInkomendeLeveringId() {
        return inkomendeLeveringId;
    }

    public long getOntvangerPersoneelslidId() {
        return ontvangerPersoneelslidId;
    }

    public LocalDateTime getLeverDatum() {
        return leverDatum;
    }

    public LocalDateTime getLeveringsbondatum() {
        return leveringsbondatum;
    }

    public String getLeveringsbonNummer() {
        return leveringsbonNummer;
    }

    public long getLeveringId() {
        return leveringId;
    }
}

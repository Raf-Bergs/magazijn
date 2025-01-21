package com.prularia.magazijn.bestelling;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BestellingRepository {
    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    void rondBestellingAf(long bestelId) {
        var sql = """
                  update bestellingen
                  set bestellingsStatusId =
                      (select bestellingsStatusId from bestellingsstatussen where naam = 'Onderweg')
                  where bestelId = ?
                  """;
        if (jdbcClient.sql(sql).param(bestelId).update() == 0) {
            throw new BestellingNietGevondenException(bestelId);
        }
    }
}

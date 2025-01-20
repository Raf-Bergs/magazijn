package com.prularia.magazijn.bestelling;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BestellijnRepository {
    private final JdbcClient jdbcClient;

    public BestellijnRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<BestellijnDTO> findByBestelId(long bestelId) {
        var sql = """
                  select bestellijnId, bestelId, bestellijnen.artikelId, naam, aantalBesteld - aantalGeannuleerd as aantal
                  from bestellijnen
                  inner join artikelen on artikelen.artikelId = bestellijnen.artikelId
                  where bestelId = ?
                  """;
        return jdbcClient.sql(sql).param(bestelId).query(BestellijnDTO.class).list();
    }
}

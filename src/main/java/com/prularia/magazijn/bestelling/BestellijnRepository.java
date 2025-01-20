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
                  select bestellijnen.artikelId, naam, beschrijving, aantalBesteld - aantalGeannuleerd as aantal
                  from bestellijnen
                  inner join artikelen on artikelen.artikelId = bestellijnen.artikelId
                  where bestelId = ? and aantalBesteld > aantalGeannuleerd
                  """;
        return jdbcClient.sql(sql).param(bestelId).query(BestellijnDTO.class).list();
    }
}

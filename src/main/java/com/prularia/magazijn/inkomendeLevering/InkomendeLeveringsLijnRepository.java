package com.prularia.magazijn.inkomendeLevering;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class InkomendeLeveringsLijnRepository {

    private final JdbcClient jdbcClient;

    public InkomendeLeveringsLijnRepository(JdbcClient jdbcClient) {this.jdbcClient = jdbcClient;}

    public int createInkomendeLeveringsLijn(InkomendeLeveringsLijn inkomendeLeveringsLijn) {
        var sql = """
                INSERT INTO inkomendeLeveringsLijnen (inkomendeLeveringsId, artikelId, aantalGoedgekeurd, aantalTeruggestuurd, magazijnPlaatsId)
                VALUES (?, ?, ?, ?, ?);
                """;
        return jdbcClient.sql(sql).params(inkomendeLeveringsLijn.getInkomendeLeveringsId(), inkomendeLeveringsLijn.getArtikelId(), inkomendeLeveringsLijn.getAantalGoedgekeurd(), inkomendeLeveringsLijn.getAantalTeruggestuurd(), inkomendeLeveringsLijn.getMagazijnPlaatsId()).update();
    }


}

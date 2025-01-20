package com.prularia.magazijn.bestelling;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BestellingRepository {

    private final JdbcClient jdbcClient;
    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    public Optional<Bestelling> findBestellingById(long bestelId) {
        var sql = """
                    SELECT bestelId, bestelDatum, klantId, betaald, betalingscode, betaalwijzeId, annulatie, annulatiedatum,
                           terugbetalingscode, bestellingsStatusId, actiecodeGebruikt, bedrijfsnaam, btwNummer, voornaam,
                           familienaam, facturatieAdresId, leveringsAdresId
                    FROM bestellingen
                    WHERE bestelId = ?
                """;
        return jdbcClient.sql(sql)
                .param(bestelId)
                .query(Bestelling.class)
                .optional();
    }
}
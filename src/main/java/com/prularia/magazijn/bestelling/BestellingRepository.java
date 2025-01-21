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

    public Optional<Bestelling> findNextBestelling() {
        var sql = """
            SELECT bestelId, bestelDatum, klantId, betaald, betalingscode, terugbetalingscode,
                   bestellingsStatusId, actiecodeGebruikt, bedrijfsnaam, btwNummer, voornaam, familienaam,
                   facturatieAdresId, leveringsAdresId
            FROM bestellingen
            WHERE bestellingsStatusId = 4 -- Alleen bestellingen met status 'Klaarmaken'
            ORDER BY bestelDatum ASC
            LIMIT 1
            """;
        return jdbcClient.sql(sql).query(Bestelling.class).optional();
    }

    public void updateStatus(long bestelId, int statusId) {
        var sql = """
                UPDATE bestellingen
                SET bestellingsStatusId = ?
                WHERE bestelId = ?
                """;
        jdbcClient.sql(sql)
                .param(statusId)
                .param(bestelId)
                .update();
    }
}
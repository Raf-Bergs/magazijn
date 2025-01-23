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


    //eerste volgende bestelling zoeken
    public Optional<Long> findBestelling() {
        var sql = """
            SELECT bestelId
            FROM bestellingen
            WHERE bestellingsStatusId in (2, 4)-- Alleen bestellingen met status 'Betaald'
            ORDER BY bestelDatum ASC
            LIMIT 1
            """;
        return jdbcClient.sql(sql).query(Long.class).optional();
    }



    public void updateStatusToKlaarmaken(long bestelId) {
        var sql= """
                UPDATE bestellingen
                SET bestellingsStatusId = 4  -- status verandert naar 'klaarmaken(4)'
                WHERE bestelId = ?
                """;
        jdbcClient.sql(sql)
                .params(bestelId)
                .update();
    }


}
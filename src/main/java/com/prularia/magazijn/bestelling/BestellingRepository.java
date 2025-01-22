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
            WHERE bestellingsStatusId = 4 -- Alleen bestellingen met status 'Klaarmaken'
            ORDER BY bestelDatum ASC
            LIMIT 1
            """;
        return jdbcClient.sql(sql).query(Long.class).optional();
    }

    //wanneer magazijnier op voltooid klikt
    public void updateStatus(long bestelId) {
        var sql = """
                UPDATE bestellingen
                SET bestellingsStatusId = 5 --status verandert naar onderweg(5)
                WHERE bestelId = ?
                """;
        jdbcClient.sql(sql)
                .param(bestelId)
                .update();
    }
}
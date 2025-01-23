package com.prularia.magazijn.leverancier;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeverancierRepository {
    private final JdbcClient jdbcClient;

    public LeverancierRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Leverancier> findAllLeveranciers() {
        var sql = """
                SELECT leveranciersId, naam, btwNummer, straat, huisNummer, bus, plaatsId, familienaamContactpersoon, voornaamContactpersoon
                FROM  leveranciers
                ORDER BY leveranciersId
                """;

        return jdbcClient.sql(sql).query(Leverancier.class).list();
    }
}

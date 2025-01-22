package com.prularia.magazijn.artikel;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class ArtikelRepository {
    private final JdbcClient jdbcClient;

    public ArtikelRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    Optional<Artikel> findById(long id) {
        var sql = """
                select artikelId, ean, naam, beschrijving, gewichtInGram, voorraad, levertijd, aantalBesteldLeverancier, maxAantalInMagazijnPLaats, leveranciersId
                from artikelen
                where artikelId = ?
                """;
        return jdbcClient.sql(sql).param(id).query(Artikel.class).optional();
    }
}

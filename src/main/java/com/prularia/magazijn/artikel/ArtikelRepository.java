package com.prularia.magazijn.artikel;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ArtikelRepository {
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

    public Optional<ArtikelDTO> findArtikelIdByEAN(String ean) {
        var sql = """
                select artikelId,ean, naam, beschrijving, voorraad
                from artikelen
                where ean = ?
                """;
        return jdbcClient.sql(sql)
                .param(ean).query(ArtikelDTO.class)
                .optional();
    }

    public void pasStockAan(long artikelId, long aantal) {
        var sql = """
                  update artikelen
                  set voorraad = voorraad - ?
                  where artikelId = ? and voorraad >= ?
                  """;
        if (jdbcClient.sql(sql).params(aantal, artikelId, aantal).update() == 0) {
            var sqlArtikelId = """
                               select voorraad
                               from artikelen
                               where artikelId = ?
                               """;
            var result = jdbcClient.sql(sqlArtikelId).param(artikelId).query(Long.class).optional();
            var voorraad = result.orElseThrow(() -> new ArtikelNietGevondenException(artikelId));
            if (voorraad < aantal) {
                throw new OnvoldoendeVoorraadException(artikelId, aantal);
            }
        }
    }
}

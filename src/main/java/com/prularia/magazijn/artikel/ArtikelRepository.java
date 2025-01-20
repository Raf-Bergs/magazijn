package com.prularia.magazijn.artikel;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ArtikelRepository {
    private final JdbcClient jdbcClient;

    public ArtikelRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
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

package com.prularia.magazijn.magazijnplaats;

import com.prularia.magazijn.artikel.ArtikelNietGevondenException;
import com.prularia.magazijn.artikel.OnvoldoendeVoorraadException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MagazijnplaatsRepository {
    private final JdbcClient jdbcClient;

    public MagazijnplaatsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void pasMagazijnplaatsAan(long magazijnplaatsId, long aantal) {
        var sql = """
                  update magazijnplaatsen
                  set aantal = aantal - ?
                  where magazijnPlaatsId = ? and aantal >= ?
                  """;
        if (jdbcClient.sql(sql).params(aantal, magazijnplaatsId, aantal).update() == 0) {
            var sqlMagazijnplaatsId = """
                               select aantal
                               from magazijnplaatsen
                               where magazijnPlaatsId = ?
                               """;
            var result = jdbcClient.sql(sqlMagazijnplaatsId).param(magazijnplaatsId).query(Long.class).optional();
            var magazijnplaatsAantal = result.orElseThrow(() -> new MagazijnplaatsNietGevondenException(magazijnplaatsId));
            if (magazijnplaatsAantal < aantal) {
                throw new OnvoldoendeVoorraadException(magazijnplaatsId, aantal);
            }
        }
    }
}

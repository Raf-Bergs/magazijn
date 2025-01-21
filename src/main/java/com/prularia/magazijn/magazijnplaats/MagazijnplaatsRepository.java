package com.prularia.magazijn.magazijnplaats;

import com.prularia.magazijn.artikel.OnvoldoendeVoorraadException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MagazijnplaatsRepository {
    private final JdbcClient jdbcClient;

    public MagazijnplaatsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Long> findIdByPlaats(String rij, int rek) {
        var sql = """
                  select magazijnPlaatsId
                  from magazijnplaatsen
                  where rij = ? and rek = ?
                  """;
        return jdbcClient.sql(sql).params(rij, rek).query(Long.class).optional();
    }

    public void pasMagazijnplaatsAan(long magazijnPlaatsId, long aantal) {
        var sql = """
                  update magazijnplaatsen
                  set aantal = aantal - ?
                  where magazijnPlaatsId = ? and aantal >= ?
                  """;
        if (jdbcClient.sql(sql).params(aantal, magazijnPlaatsId, aantal).update() == 0) {
            var sqlMagazijnplaatsId = """
                               select aantal
                               from magazijnplaatsen
                               where magazijnPlaatsId = ?
                               """;
            var result = jdbcClient.sql(sqlMagazijnplaatsId).param(magazijnPlaatsId).query(Long.class).optional();
            var magazijnplaatsAantal = result.orElseThrow(() -> new MagazijnplaatsNietGevondenException(magazijnPlaatsId));
            if (magazijnplaatsAantal < aantal) {
                throw new OnvoldoendeVoorraadException(magazijnPlaatsId, aantal);
            }
        }
    }
}

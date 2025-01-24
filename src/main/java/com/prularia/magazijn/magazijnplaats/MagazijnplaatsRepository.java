package com.prularia.magazijn.magazijnplaats;

import com.prularia.magazijn.artikel.OnvoldoendeVoorraadException;
import com.prularia.magazijn.pickingLocatie.PickingLocatie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MagazijnplaatsRepository {
    private final JdbcClient jdbcClient;

    public MagazijnplaatsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<PickingLocatie> findLocatiesVoorBestelling(long bestelId) {
        var sql = """
                SELECT
                      mp.artikelId, a.naam as artikelNaam, mp.magazijnPlaatsId, mp.rij, mp.rek,
                      mp.aantal AS voorraadInPlaats, bl.aantalBesteld
                FROM magazijnplaatsen mp
                JOIN artikelen a ON mp.artikelId = a.artikelId
                JOIN bestellijnen bl ON bl.artikelId = mp.artikelId
                WHERE bl.bestelId = ?
                ORDER BY bl.artikelId, mp.rij, mp.rek
                """;
        return jdbcClient.sql(sql)
                .param(bestelId)
                .query(PickingLocatie.class)
                .list();
    }

    public int vindBeschikbareVoorraad(long magazijnPlaatsId) {
        var sql = """
                SELECT aantal
                FROM magazijnplaatsen
                WHERE magazijnPlaatsId = ?
                """;
        return jdbcClient.sql(sql)
                .param(magazijnPlaatsId)
                .query(Integer.class)
                .single();
    }

    public Map<String, List<PickingLocatie>> findGroupedByCellAndOrdered(long bestelId) {
        var sql = """
        SELECT
            mp.artikelId, a.naam as artikelNaam, mp.magazijnPlaatsId, mp.rij, mp.rek, mp.aantal AS voorraadInPlaats,
            bl.aantalBesteld, a.beschrijving
        FROM magazijnplaatsen mp
        JOIN artikelen a ON mp.artikelId = a.artikelId
        JOIN bestellijnen bl ON bl.artikelId = mp.artikelId
        WHERE bl.bestelId = ?
        ORDER BY mp.rij, mp.rek, bl.artikelId
    """;
        List<PickingLocatie> locaties = jdbcClient.sql(sql)
                .param(bestelId)
                .query(PickingLocatie.class)
                .list();

        return locaties.stream().collect(Collectors.groupingBy(
                locatie -> locatie.getRij() + "-" + locatie.getRek()
        ));
    }

    public Optional<Long> findIdByPlaats(String rij, int rek) {
        var sql = """
                  select magazijnPlaatsId
                  from magazijnplaatsen
                  where rij = ? and rek = ?
                  """;
        return jdbcClient.sql(sql).params(rij, rek).query(Long.class).optional();
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


package com.prularia.magazijn.InkomendeLeveringsLijn;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InkomendeLeveringsLijnRepository{
    private final JdbcClient jdbcClient;

    public InkomendeLeveringsLijnRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<InkomendeLeveringslijnDTO> getLeveringslijnenSortedByMagazijnplaatsId() {
        var sql = """
                   select il.inkomendeLeveringsId, il.artikelId, il.aantalGoedgekeurd, il.aantalTeruggestuurd, il.magazijnPlaatsId,
                           concat(mp.rij, mp.rek) as plaats
                    from inkomendeleveringslijnen il
                    join magazijnplaatsen mp on il.magazijnPlaatsId = mp.magazijnPlaatsId
                    order by il.magazijnPlaatsId
                """;
        return jdbcClient.sql(sql)
                .query(InkomendeLeveringslijnDTO.class)
                .list();
    }


}

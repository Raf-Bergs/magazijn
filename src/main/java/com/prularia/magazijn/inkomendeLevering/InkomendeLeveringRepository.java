package com.prularia.magazijn.inkomendeLevering;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class InkomendeLeveringRepository {
    private final JdbcClient jdbcClient;

    public InkomendeLeveringRepository(JdbcClient jdbcClient) {this.jdbcClient = jdbcClient;}

    //var keyHolder = new GeneratedKeyHolder();
    //jdbcClient.sql(sql)
    //.params(...)
    //.update(keyHolder);
    //return keyHolder.getKey().longValue();
    public int createInkomendeLevering(InkomendeLevering inkomendeLevering) {
        var sql = """
                INSERT INTO inkomendeLeveringen (leveranciersId, leveringsbonNummer, leveringsbondatum, leverDatum, ontvangerPersoneelslidId)
                VALUES (?, ?, ?, ?, ?);
                """;
        return jdbcClient.sql(sql)
                .params(inkomendeLevering.getLeveranciersId(), inkomendeLevering.getLeveringsbonNummer(), inkomendeLevering.getLeveringsbondatum(),inkomendeLevering.getLeverDatum(), inkomendeLevering.getOntvangerPersoneelslidId())
                .update();
    }


}

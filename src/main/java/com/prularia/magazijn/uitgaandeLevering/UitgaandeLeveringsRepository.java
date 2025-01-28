package com.prularia.magazijn.uitgaandeLevering;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UitgaandeLeveringsRepository {
    private final JdbcClient jdbcClient;

    public UitgaandeLeveringsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public long createUitgaandeLevering(UitgaandeLevering uitgaandeLevering) {
        var sql = """
                insert into uitgaandeleveringen (bestelId, vertrekDatum, klantId, uitgaandeLeveringsStatusId)
                values (?, ?, ?, ?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql).params(uitgaandeLevering.getBestelId(), uitgaandeLevering.getVertrekDatum(), uitgaandeLevering.getKlantId(), uitgaandeLevering.getUitgaandeLeveringsStatusId()).update(keyHolder);
        return keyHolder.getKey().longValue();
    }
}

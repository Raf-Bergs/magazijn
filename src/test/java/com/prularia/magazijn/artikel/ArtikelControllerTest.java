package com.prularia.magazijn.artikel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
//@Sql("/artikelen.sql")
@AutoConfigureMockMvc
class ArtikelControllerTest {
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public ArtikelControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    /*private long idTestPrul1() {
        return jdbcClient.sql("select artikelId from artikelen where naam = 'testPrul1'").query(Long.class).single();
    }*/

    @Test
    void findByIdWithExistingIdFindsTheArtikel() throws Exception {
        mockMvc.perform(get("/artikelen/{id}", 1)).andExpectAll(
                status().isOk(),
                jsonPath("artikelId").value(1),
                jsonPath("ean").value("5499999000019"));
    }

    @Test
    void findByIdWithNonExistingIdThrowsNotFound() throws Exception {
        mockMvc.perform(get("/artikelen/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
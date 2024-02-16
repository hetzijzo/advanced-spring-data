package nl.jdriven.springdata;

import nl.jdriven.springdata.entity.Transactie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TransactieRepository {

    //Zoeken naar transacties van een klant, met een klantnr. Zoeken kan op basis van omschrijving, bedrag en tegenrekening.

    private final JdbcClient jdbcClient;

    private final RowMapper<Transactie> transactieRowMapper = (rs, rowNum) -> new Transactie(
            UUID.fromString(rs.getString("transactie_nr")),
            UUID.fromString(rs.getString("rekening_nr")),
            rs.getString("iban"),
            rs.getString("iban_tegenrekening"),
            rs.getBigDecimal("bedrag"),
            rs.getString("omschrijving"));

    public TransactieRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Transactie> getTransacties(String klantNr) {
        return jdbcClient.sql("SELECT * FROM TRANSACTIE WHERE rekening_nr IN (SELECT rekening_nr from REKENING where klant_nr = :klant_nr)")
                .param("klant_nr", klantNr)
                .query(transactieRowMapper).list();
    }

    public List<Transactie> findTransacties(String klantNr, String omschrijving) {
        return jdbcClient.sql("SELECT * FROM TRANSACTIE WHERE omschrijving LIKE :omschrijving " +
                        "AND rekening_nr IN (SELECT rekening_nr from REKENING where klant_nr = :klant_nr)")
                .param("klant_nr", klantNr)
                .param("omschrijving", omschrijving)
                .query(transactieRowMapper).list();
    }
}

package nl.jdriven.springdata;

import nl.jdriven.springdata.entity.Klant;
import nl.jdriven.springdata.entity.Transactie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Types;
import java.time.Clock;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class AdvancedSpringDataApplicationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private JdbcClient jdbcClient;

    @Autowired
    private TransactieRepository transactieRepository;

    private Clock clock = Clock.systemUTC();

    private List<Klant> klanten;

    @BeforeEach
    void insertData() {
        klanten = TestDataFactory.getInstance().generateKlanten();
        klanten.forEach(klant ->
                jdbcClient.sql("INSERT INTO KLANT (klant_nr, voornaam, achternaam) " +
                                "VALUES (:klant_nr, :voornaam, :achternaam)")
                        .param("klant_nr", klant.getKlantNr(), Types.VARCHAR)
                        .param("voornaam", klant.getVoornaam(), Types.VARCHAR)
                        .param("achternaam", klant.getAchternaam(), Types.VARCHAR)
                        .update());

        klanten.stream()
                .map(TestDataFactory.getInstance()::generateRekeningen)
                .flatMap(Collection::stream)
                .forEach(rekening -> {
                    jdbcClient.sql("INSERT INTO REKENING (rekening_nr, klant_nr, iban, saldo) " +
                                    "VALUES (:rekening_nr, :klant_nr, :iban, :saldo)")
                            .param("rekening_nr", rekening.getRekeningNr(), Types.VARCHAR)
                            .param("klant_nr", rekening.getKlantNr(), Types.VARCHAR)
                            .param("iban", rekening.getIban(), Types.VARCHAR)
                            .param("saldo", rekening.getSaldo(), Types.NUMERIC)
                            .update();
                    TestDataFactory.getInstance().generateTransacties(rekening)
                            .forEach(transactie ->
                                    jdbcClient.sql("INSERT INTO TRANSACTIE (transactie_nr, rekening_nr, iban, iban_tegenrekening, bedrag, omschrijving) " +
                                                    "VALUES (:transactie_nr, :rekening_nr, :iban, :iban_tegenrekening, :bedrag, :omschrijving)")
                                            .param("transactie_nr", transactie.getTransactieNr(), Types.VARCHAR)
                                            .param("rekening_nr", transactie.getRekeningNr(), Types.VARCHAR)
                                            .param("iban", transactie.getIban(), Types.VARCHAR)
                                            .param("iban_tegenrekening", transactie.getIbanTegenRekening(), Types.VARCHAR)
                                            .param("bedrag", transactie.getBedrag(), Types.NUMERIC)
                                            .param("omschrijving", transactie.getOmschrijving(), Types.VARCHAR)
                                            .update());
                });
    }

    @Test
    void doesTablesExist() {
        int nrOfKlanten = jdbcClient.sql("SELECT count(*) FROM KLANT").query(Integer.class).single();
        assertThat(nrOfKlanten).isPositive();

        int nrOfRekeningen = jdbcClient.sql("SELECT count(*) FROM REKENING").query(Integer.class).single();
        assertThat(nrOfRekeningen).isPositive();

        int nrOfTransacties = jdbcClient.sql("SELECT count(*) FROM TRANSACTIE").query(Integer.class).single();
        assertThat(nrOfTransacties).isPositive();

        //Test
        var start = clock.instant();
        System.out.println("Starting select");
        System.out.println("Klanten: %d".formatted(nrOfKlanten));
        System.out.println("Rekeningen: %d".formatted(nrOfRekeningen));
        System.out.println("Transacties: %d".formatted(nrOfTransacties));

        List<Transactie> transacties = transactieRepository.getTransacties(klanten.stream().findFirst().get().getKlantNr().toString());
        assertThat(transacties).isNotEmpty();

        System.out.println("Select Done after %d ms".formatted(Duration.between(start, clock.instant()).toMillis()));
    }
}

package nl.jdriven.springdata;

import net.datafaker.Faker;
import net.datafaker.transformations.JavaObjectTransformer;
import nl.jdriven.springdata.entity.Klant;
import nl.jdriven.springdata.entity.Rekening;
import nl.jdriven.springdata.entity.Transactie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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

    private final Faker faker = new Faker();
    private final JavaObjectTransformer transformer = new JavaObjectTransformer();

    @Test
    void doesTablesExist() {
        int nrOfKlanten = jdbcClient.sql("SELECT count(*) FROM KLANT").query(Integer.class).single();
        assertThat(nrOfKlanten).isZero();

        int nrOfRekeningen = jdbcClient.sql("SELECT count(*) FROM REKENING").query(Integer.class).single();
        assertThat(nrOfRekeningen).isZero();

        int nrOfTransacties = jdbcClient.sql("SELECT count(*) FROM TRANSACTIE").query(Integer.class).single();
        assertThat(nrOfTransacties).isZero();
    }

    @Test
    //BeforeAll?
    void createDataInDatabase() {
        List<Klant> klanten = TestDataFactory.getInstance().generateKlanten();

        List<Rekening> rekeningen = klanten.stream()
                .map(TestDataFactory.getInstance()::generateRekeningen)
                .flatMap(Collection::stream)
                .toList();

        List<Transactie> transacties = rekeningen.stream()
                .map(TestDataFactory.getInstance()::generateTransacties)
                .flatMap(Collection::stream)
                .toList();

        //Add data to database
    }
}

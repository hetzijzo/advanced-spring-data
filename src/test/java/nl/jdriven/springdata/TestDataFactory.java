package nl.jdriven.springdata;

import net.datafaker.Faker;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;
import nl.jdriven.springdata.entity.Klant;
import nl.jdriven.springdata.entity.Rekening;
import nl.jdriven.springdata.entity.Transactie;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static net.datafaker.transformations.Field.field;

public class TestDataFactory {

    private static TestDataFactory instance;

    private final Faker faker = new Faker();
    private final JavaObjectTransformer transformer = new JavaObjectTransformer();

    private static final int minKlanten = 10;
    private static final int maxKlanten = 1000;
    private static final int minRekeningenPerKlant = 1;
    private static final int maxRekeningenPerKlant = 10;
    private static final int minTransactiesPerRekening = 100;
    private static final int maxTransactiesPerRekening = 1000;

    public static TestDataFactory getInstance() {
        if (instance == null) {
            TestDataFactory.instance = new TestDataFactory();
        }
        return instance;
    }

    public List<Klant> generateKlanten() {
        Schema<Object, ?> schema = Schema.of(
                field("klantNr", UUID::randomUUID),
                field("voornaam", () -> faker.name().firstName()),
                field("achternaam", () -> faker.name().lastName()));

        return faker.collection(() -> transformer.apply(Klant.class, schema))
                .minLen(minKlanten)
                .maxLen(maxKlanten)
                .generate();
    }

    public List<Rekening> generateRekeningen(Klant klant) {
        Schema<Object, ?> schema = Schema.of(
                field("rekeningNr", UUID::randomUUID),
                field("klantNr", klant::getKlantNr),
                field("iban", () -> faker.finance().iban("NL")),
                field("saldo", () -> BigDecimal.valueOf(faker.number().randomDouble(2, 0L, 10000L))));

        return faker.collection(() -> transformer.apply(Rekening.class, schema))
                .minLen(minRekeningenPerKlant)
                .maxLen(maxRekeningenPerKlant)
                .generate();
    }

    public List<Transactie> generateTransacties(Rekening rekening) {
        Schema<Object, ?> schema = Schema.of(
                field("transactieNr", UUID::randomUUID),
                field("rekeningNr", rekening::getRekeningNr),
                field("iban", rekening::getIban),
                field("ibanTegenRekening", () -> faker.finance().iban("NL")),
                field("bedrag", () -> BigDecimal.valueOf(faker.number().randomDouble(2, 0L, 1200L))),
                field("omschrijving", () -> faker.text().text(25, 255)));

        return faker.collection(() -> transformer.apply(Transactie.class, schema))
                .minLen(minTransactiesPerRekening)
                .maxLen(maxTransactiesPerRekening)
                .generate();
    }
}

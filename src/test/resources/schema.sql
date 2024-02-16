DROP TABLE IF EXISTS KLANT;
CREATE TABLE KLANT(
    KLANT_NR varchar(255) NOT NULL,
    VOORNAAM varchar(255) NOT NULL,
    ACHTERNAAM varchar(255) NOT NULL,
    PRIMARY KEY (KLANT_NR)
);

DROP TABLE IF EXISTS REKENING;
CREATE TABLE REKENING(
    REKENING_NR varchar(255) NOT NULL,
    KLANT_NR varchar(255) NOT NULL,
    IBAN varchar(255) NOT NULL,
    SALDO NUMERIC(12,2) NOT NULL,
    PRIMARY KEY (REKENING_NR),
    CONSTRAINT FK_REKENING_CUSTOMER
        FOREIGN KEY(KLANT_NR)
        REFERENCES KLANT(KLANT_NR)
);

DROP TABLE IF EXISTS TRANSACTIE;
CREATE TABLE TRANSACTIE(
    TRANSACTIE_NR varchar(255) NOT NULL,
    REKENING_NR varchar(255) NOT NULL,
    IBAN varchar(255) NOT NULL,
    IBAN_TEGENREKENING varchar(255) NOT NULL,
    BEDRAG NUMERIC(12,2) NOT NULL,
    OMSCHRIJVING varchar(255),
    CONSTRAINT FK_TRANSACTIE_REKENING
        FOREIGN KEY(REKENING_NR)
        REFERENCES REKENING(REKENING_NR)
);


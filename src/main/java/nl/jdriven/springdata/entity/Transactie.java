package nl.jdriven.springdata.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Transactie {

    private UUID transactieNr;
    private UUID rekeningNr;
    private String iban;
    private String ibanTegenRekening;
    private BigDecimal bedrag;
    private String omschrijving;

    public Transactie(UUID transactieNr, UUID rekeningNr, String iban, String ibanTegenRekening, BigDecimal bedrag, String omschrijving) {
        this.transactieNr = transactieNr;
        this.rekeningNr = rekeningNr;
        this.iban = iban;
        this.ibanTegenRekening = ibanTegenRekening;
        this.bedrag = bedrag;
        this.omschrijving = omschrijving;
    }

    public UUID getTransactieNr() {
        return transactieNr;
    }

    public void setTransactieNr(UUID transactieNr) {
        this.transactieNr = transactieNr;
    }

    public UUID getRekeningNr() {
        return rekeningNr;
    }

    public void setRekeningNr(UUID rekeningNr) {
        this.rekeningNr = rekeningNr;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIbanTegenRekening() {
        return ibanTegenRekening;
    }

    public void setIbanTegenRekening(String ibanTegenRekening) {
        this.ibanTegenRekening = ibanTegenRekening;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}

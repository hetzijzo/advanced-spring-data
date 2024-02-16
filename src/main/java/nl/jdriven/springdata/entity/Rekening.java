package nl.jdriven.springdata.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Rekening {

    private UUID rekeningNr;
    private UUID klantNr;
    private String iban;
    private BigDecimal saldo;

    public UUID getRekeningNr() {
        return rekeningNr;
    }

    public void setRekeningNr(UUID rekeningNr) {
        this.rekeningNr = rekeningNr;
    }

    public UUID getKlantNr() {
        return klantNr;
    }

    public void setKlantNr(UUID klantNr) {
        this.klantNr = klantNr;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}

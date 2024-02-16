package nl.jdriven.springdata.entity;

import java.util.UUID;

public class Klant {

    private UUID klantNr;
    private String voornaam;
    private String achternaam;

    public UUID getKlantNr() {
        return klantNr;
    }

    public void setKlantNr(UUID klantNr) {
        this.klantNr = klantNr;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
}

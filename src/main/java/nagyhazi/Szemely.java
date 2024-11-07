package nagyhazi;

import java.io.Serializable;

class Szemely implements Serializable{
    private String nev;
    private String foglalkozas;
    private String telefonszam;

    public Szemely(String nev, String foglalkozas, String telefonszam) {
        this.nev = nev;
        this.foglalkozas = foglalkozas;
        this.telefonszam = telefonszam;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getFoglalkozas() {
        return foglalkozas;
    }

    public void setFoglalkozas(String foglalkozas) {
        this.foglalkozas = foglalkozas;
    }

    public String getTelefonszam() {
        return telefonszam;
    }

    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

    @Override
    public String toString() {
        return nev + ", " + foglalkozas + ", " + telefonszam;
    }
}
package nagyhazi;

import java.io.Serializable;

class Szemely implements Serializable{
    private String nev;
    private String foglalkozas;
    private String telefonszam;

    // Konstrukto
    public Szemely(String nev, String foglalkozas, String telefonszam) {
        this.nev = nev;
        this.foglalkozas = foglalkozas;
        this.telefonszam = telefonszam;
    }

    // Visszaadja a szemely nevet
    public String getNev() {
        return nev;
    }

    // Beallitja a szemely nevet
    public void setNev(String nev) {
        this.nev = nev;
    }

    // Visszaadja a szemely foglalkozasat
    public String getFoglalkozas() {
        return foglalkozas;
    }

    // Beallitja a szemely foglalkozasat
    public void setFoglalkozas(String foglalkozas) {
        this.foglalkozas = foglalkozas;
    }

    // Visszaadja a szemely telefonszamat
    public String getTelefonszam() {
        return telefonszam;
    }

    // Beallitja a szemely telefonszamat
    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

    // Visszaadja a kimenetre a szemely adatait
    @Override
    public String toString() {
        return nev + ", " + foglalkozas + ", " + telefonszam;
    }
}
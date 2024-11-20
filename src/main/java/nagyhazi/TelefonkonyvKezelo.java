package nagyhazi;

import java.util.ArrayList;

// A telefonkonyv kezelo osztaly,amely lehetove teszi szemelyek hozzaadasat,modositasat, torleset es keresest
class TelefonkonyvKezelo {
    private ArrayList<Szemely> telefonkonyv;

    // Konstruktor
    public TelefonkonyvKezelo() {
        telefonkonyv = new ArrayList<>();
    }

    // Visszaadja a telefonkonyvet
    public ArrayList<Szemely> getTelefonkonyv() {
        return telefonkonyv;
    }

    // Hozzaad egy szemelyt a telefonkonyvhoz
    public void addSzemely(Szemely szemely) {
        telefonkonyv.add(szemely);
    }

    // Modosit egy szemelyt a telefonkonyvben a megadott indexen
    public void modifySzemely(int index, Szemely szemely) {
        telefonkonyv.set(index, szemely);
    }

    // Torol egy szemelyt a telefonkonyvbol a megadott indexen
    public void removeSzemely(int index) {
        telefonkonyv.remove(index);
    }

    // Keres a telefonkonyvben a megadott szoveg alapjan.
    public ArrayList<Szemely> search(String keresettSzoveg) {
        ArrayList<Szemely> talalatok = new ArrayList<>();
        for (Szemely szemely : telefonkonyv) {
            if (szemely.getNev().contains(keresettSzoveg) || szemely.getFoglalkozas().contains(keresettSzoveg)
                    || szemely.getTelefonszam().contains(keresettSzoveg)) {
                talalatok.add(szemely);
            }
        }
        return talalatok;
    }

    public void clearTelefonkonyv() {
        telefonkonyv.clear();
    }
}

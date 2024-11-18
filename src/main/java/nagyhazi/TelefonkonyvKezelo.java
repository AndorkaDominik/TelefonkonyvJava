package nagyhazi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

// A telefonkonyv kezelo osztaly,amely lehetove teszi szemelyek hozzaadasat,modositasat, torleset es keresest.
// Az osztaly implementalja a Serializable interfeszt,hogy lehetove tegye az objektumok fajlba mentését es betolteset.
class TelefonkonyvKezelo implements Serializable{
    private ArrayList<Szemely> telefonkonyv;

    // Konstruktor
    public TelefonkonyvKezelo() {
        telefonkonyv = new ArrayList<>();
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

    // Visszaadja a telefonkonyvet
    public ArrayList<Szemely> getTelefonkonyv() {
        return telefonkonyv;
    }

    // Menti a telefonkonyvet egy fajlba.
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(telefonkonyv);
        }
    }

    // Betolti a telefonkonyvet egy fajlbol.
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            telefonkonyv = (ArrayList<Szemely>) ois.readObject();
        }
    }
}

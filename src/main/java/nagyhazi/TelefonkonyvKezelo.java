package nagyhazi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class TelefonkonyvKezelo implements Serializable{
    private ArrayList<Szemely> telefonkonyv;

    public TelefonkonyvKezelo() {
        telefonkonyv = new ArrayList<>();
    }

    public void addSzemely(Szemely szemely) {
        telefonkonyv.add(szemely);
    }

    public void modifySzemely(int index, Szemely szemely) {
        telefonkonyv.set(index, szemely);
    }

    public void removeSzemely(int index) {
        telefonkonyv.remove(index);
    }

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

    public ArrayList<Szemely> getTelefonkonyv() {
        return telefonkonyv;
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(telefonkonyv);
        }
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            telefonkonyv = (ArrayList<Szemely>) ois.readObject();
        }
    }
}

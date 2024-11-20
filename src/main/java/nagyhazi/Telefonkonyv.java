package nagyhazi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Telefonkonyv extends JFrame {

    public TelefonkonyvKezelo telefonkonyvKezelo;
    private JTable tabla;
    private DefaultTableModel tablaModel;

    // Inicializalja a telefonkonyvet
    public Telefonkonyv() {
        initializePhonebook();
    }


    public void initializePhonebook() {
        // Az alkalmazás teljesen leall, amikor az ablak bezarasra kerul
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Letrehoz egy telefonkonyvKezelot
        telefonkonyvKezelo = new TelefonkonyvKezelo();

        // Pelda adatok hozzaadasa
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "12345678910"));
        telefonkonyvKezelo.addSzemely(new Szemely("Nagy Anna", "Tanár", "98765432110"));
        telefonkonyvKezelo.addSzemely(new Szemely("Szabó Péter", "Orvos", "55555555510"));
        telefonkonyvKezelo.addSzemely(new Szemely("Tóth Gábor", "Számítógépes Mérnök", "22233344410"));
        telefonkonyvKezelo.addSzemely(new Szemely("Kiss Mária", "Ügyvéd", "44455566610"));
        telefonkonyvKezelo.addSzemely(new Szemely("Farkas László", "Építész", "77788899910"));
        telefonkonyvKezelo.addSzemely(new Szemely("Molnár Éva", "Marketing Szakértő", "11122233310"));
        telefonkonyvKezelo.addSzemely(new Szemely("Papp Zoltán", "Képzőművész", "33344455510"));
        telefonkonyvKezelo.addSzemely(new Szemely("Varga Balázs", "Mérnök", "66677788810"));
        telefonkonyvKezelo.addSzemely(new Szemely("Horváth Klára", "Újságíró", "99900011110"));

        // Ablak nevenek beallitasa
        setTitle("Telefonkönyv");
        // Ablak merete beallitasa
        setSize(1150, 500);
        // Ablak a kepernyo kozepen jelenjen meg
        setLocationRelativeTo(null);

        // Elrendezes beallitasa
        setLayout(new BorderLayout());

        // Letre hozza a tablamodellt es a tablat
        tablaModel = new DefaultTableModel(new Object[] { "Név", "Foglalkozás", "Telefonszám" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // A cellák nem módosíthatóak
            }
        };
        tabla = new JTable(tablaModel);
        // Letrehoz egy gorgetheto panelt, amely tartalmazza a tablat
        JScrollPane scrollPane = new JScrollPane(tabla);
        // Hozzaadja a gorgetheto panelt az ablak kozepehez
        add(scrollPane, BorderLayout.CENTER);

        // Alapveto adatok betoltese
        loadTableData();

        // Letrehozza a gombpanelt
        JPanel buttonPanel = new JPanel();
        // Grid elrendezessel beallitja a sor oszlop szamossagot, valamint a kozt a
        // gombok kozott
        buttonPanel.setLayout(new GridLayout(1, 7, 5, 5));

        // Gombok letrehozasa nem alap kinezettel
        JButton ujRekordButton = createStyledButton("Új rekord létrehozása");
        JButton modositButton = createStyledButton("Módosítás");
        JButton torlesButton = createStyledButton("Törlés");
        JButton keresButton = createStyledButton("Keresés");
        JButton detailsButton = createStyledButton("Rekord részletei");
        JButton importButton = createStyledButton("Adatok importálása");
        JButton mentesButton = createStyledButton("Fájlba mentés");

        // Esemenykezelok hozzaadasa a gombokhoz
        ujRekordButton.addActionListener(e -> ujRekord());
        modositButton.addActionListener(e -> modositRekord());
        torlesButton.addActionListener(e -> torolRekord());
        keresButton.addActionListener(e -> keresRekord());
        detailsButton.addActionListener(e -> showRecordDetails());
        importButton.addActionListener(e -> {
            String filePath = JOptionPane.showInputDialog(this, "Adja meg a fájl nevét:");
            if (filePath != null && !filePath.trim().isEmpty()) {
                importFromCSV(filePath);
            }
        });
        mentesButton.addActionListener(e -> {
            String filePath = JOptionPane.showInputDialog(this, "Adja meg a fájl nevét:");
            if (filePath != null && !filePath.trim().isEmpty()) {
                exportToCSV(filePath);
            }
        });

        // Gombok hozzadasa a gombpanelhez
        buttonPanel.add(ujRekordButton);
        buttonPanel.add(modositButton);
        buttonPanel.add(torlesButton);
        buttonPanel.add(keresButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(importButton);
        buttonPanel.add(mentesButton);

        // Gombpanel hozzadasa az ablakhoz aljahoz
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Letrehoz egy stilusos gombot a megadott szöveggel
    private JButton createStyledButton(String text) {
        // Letrehoz egy uj JButton objektumot a megadott szoveggel
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        // Szovegszin beallitasa
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 150, 250)); // Kék háttér
        button.setFocusPainted(false);
        return button;
    }

    // Betolti az adatokat a tablaba.
    public void loadTableData() {
        // Torli a meglevo sorokat a tablamodellbol
        tablaModel.setRowCount(0);
        // Vegigmegy a telefonkonyvben levo szemelyeken
        for (Szemely szemely : telefonkonyvKezelo.getTelefonkonyv()) {
            // Hozzaad egy uj sort a tablamodellhez a szemely adataival
            tablaModel.addRow(new Object[] { szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam() });
        }
    }

    // Ellenorzi, hogy a nev, foglalkozas es telefonszam mezők érvényesek-e
    private boolean isValidInput(String nev, String foglalkozas, String telefonszam) {
        if (!nev.matches("[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ ]+")) {
            JOptionPane.showMessageDialog(this, "A név nem tartalmazhat számokat.", "Hiba", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!foglalkozas.matches("[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ ]+")) {
            JOptionPane.showMessageDialog(this, "A foglalkozás nem tartalmazhat számokat.", "Hiba",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!telefonszam.matches("[0-9+]+")) {
            JOptionPane.showMessageDialog(this, "A telefonszám csak számokat és + jelet tartalmazhat.", "Hiba",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Ellenorzi hogy a megadott adatok nem e uresek vagy nem eleg hosszuak
    public boolean validateInput(String nev, String foglalkozas, String telefonszam) {
        return !nev.isEmpty() && !foglalkozas.isEmpty() && telefonszam.matches("\\d{11}");
    }

    // Uj rekordot hoz letre
    public void ujRekord() {
        // Letrehoz harom szovegmezot a nev, foglalkozas es telefonszam bevitelehez
        JTextField nevField = new JTextField();
        JTextField foglalkozasField = new JTextField();
        JTextField telefonszamField = new JTextField();

        // Letrehoz egy objektumtombot, amely tartalmazza a szovegmezoket és a hozzajuk
        // tartozo cimkeket
        Object[] inputFields = {
                "Név:", nevField,
                "Foglalkozás:", foglalkozasField,
                "Telefonszám:", telefonszamField
        };

        // Megjelenít egy parbeszedablakot az uj rekord hozzaadasahoz
        int option = JOptionPane.showConfirmDialog(this, inputFields, "Új rekord hozzáadása",
                JOptionPane.OK_CANCEL_OPTION);

        // Ha az OK gombra kattint
        if (option == JOptionPane.OK_OPTION) {
            // Lekeri és eltavolitja a felesleges szokozoket a bevitt adatokbol
            String nev = nevField.getText().trim();
            String foglalkozas = foglalkozasField.getText().trim();
            String telefonszam = telefonszamField.getText().trim();

            // Ellenorzi, hogy a bevitt adatok ervenyesek-e, majd hozzadja az uj szemelyt a
            // tablahoz
            if (isValidInput(nev, foglalkozas, telefonszam) && validateInput(nev, foglalkozas, telefonszam)) {
                Szemely ujSzemely = new Szemely(nev, foglalkozas, telefonszam);
                telefonkonyvKezelo.addSzemely(ujSzemely);
                loadTableData();
            } else {
                // Ha az adatok érvénytelenek, megjelenít egy hibaüzenetet
                JOptionPane.showMessageDialog(this, "Kérlek, ellenőrizd a megadott adatokat.");
            }
        }
    }

    // Modosit egy meglevo rekordot a telefonkonyvben.
    public void modositRekord() {
        // Lekeri a kivalasztott sor indexet a tablabol
        int kivalasztottSor = tabla.getSelectedRow();
        // Ellenorzi, hogy van-e kiválasztott sor
        if (kivalasztottSor != -1) {
            // Letrehoz harom szovegmezot a nev, foglalkozas es telefonszam bevitelehez, a
            // kivalasztott sor adataival feltoltve
            JTextField nevField = new JTextField((String) tablaModel.getValueAt(kivalasztottSor, 0));
            JTextField foglalkozasField = new JTextField((String) tablaModel.getValueAt(kivalasztottSor, 1));
            JTextField telefonszamField = new JTextField((String) tablaModel.getValueAt(kivalasztottSor, 2));
            // Letrehoz egy objektumtombot, amely tartalmazza a szovegmezoket es a hozzajuk
            // tartozo cimkeket
            Object[] inputFields = {
                    "Név:", nevField,
                    "Foglalkozás:", foglalkozasField,
                    "Telefonszám:", telefonszamField
            };

            // Megjelenit egy parbeszedablakot a rekord modositasahoz
            int option = JOptionPane.showConfirmDialog(this, inputFields, "Rekord módosítása",
                    JOptionPane.OK_CANCEL_OPTION);
            // Ha az OK gombra kattintanak
            if (option == JOptionPane.OK_OPTION) {
                // Lekeri és eltavolitja a felesleges szokozoket a bevitt adatokbol
                String nev = nevField.getText().trim();
                String foglalkozas = foglalkozasField.getText().trim();
                String telefonszam = telefonszamField.getText().trim();

                // Ellenorzi, hogy a bevitt adatok ervenyesek-e, majd hozzadja az uj szemelyt a tablahoz
                if (isValidInput(nev, foglalkozas, telefonszam) && validateInput(nev, foglalkozas, telefonszam)) {
                    Szemely modositottSzemely = new Szemely(nev, foglalkozas, telefonszam);
                    telefonkonyvKezelo.modifySzemely(kivalasztottSor, modositottSzemely);
                    loadTableData();
                } else {
                    // Ha az adatok érvénytelenek, megjelenít egy hibaüzenetet
                    JOptionPane.showMessageDialog(this, "Kérlek, ellenőrizd a megadott adatokat.");
                }
            }
        } else {
            // Ha az nem valasztott ki sort
            JOptionPane.showMessageDialog(this, "Válasszon ki egy rekordot a módosításhoz.");
        }
    }

    public void torolRekord() {
        // Lekeri a kivalasztott sor indexet a tablabol
        int kivalasztottSor = tabla.getSelectedRow();
        // Ellenorzi, hogy van-e kiválasztott sor
        if (kivalasztottSor != -1) {
            // Megerosites kerese
            int confirm = JOptionPane.showConfirmDialog(this, "Biztosan törölni szeretnéd ezt a rekordot?",
                    "Megerősítés", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                telefonkonyvKezelo.removeSzemely(kivalasztottSor);
                loadTableData();
            }
        } else {
            // Ha az nem valasztott ki sort
            JOptionPane.showMessageDialog(this, "Válasszon ki egy rekordot a törléshez.");
        }
    }

    // Keres egy rekordot a telefonkonyvben a megadott szoveg alapjan
    public void keresRekord() {
        // Megjelenit egy parbeszedablakot, ahol a felhasznalo megadhatja a keresett
        // szot
        String keresettSzoveg = JOptionPane.showInputDialog(this, "Adja meg a keresett szót:");
        // Ellenorzi, hogy a keresett szoveg nem null es nem ures
        if (keresettSzoveg != null && !keresettSzoveg.isEmpty()) {
            // Keresest vegez a telefonkonyvben a megadott szoveg alapjan
            ArrayList<Szemely> talalatok = telefonkonyvKezelo.search(keresettSzoveg);
            // Torli a meglevo sorokat a tablamodellbol
            tablaModel.setRowCount(0);
            // Vegigmegy a talalatokon és hozzaadja oket a tablamodellhez
            for (Szemely szemely : talalatok) {
                tablaModel
                        .addRow(new Object[] { szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam() });
            }
        }
    }

    // Megjeleniti a kivalasztott rekord reszleteit.
    public void showRecordDetails() {
        // Lekeri a kivalasztott sor indexet a tablabol
        int selectedRow = tabla.getSelectedRow();
        // Ellenorzi, hogy van-e kivalasztott sor
        if (selectedRow != -1) {
            // Lekeri a kivalasztott sor indexet a tablabol
            String nev = (String) tablaModel.getValueAt(selectedRow, 0);
            String foglalkozas = (String) tablaModel.getValueAt(selectedRow, 1);
            String telefonszam = (String) tablaModel.getValueAt(selectedRow, 2);

            // Letrehozza es megjeleniti a reszletek parbeszedablakot
            String details = String.format("Név: %s\nFoglalkozás: %s\nTelefonszám: %s", nev, foglalkozas, telefonszam);
            JOptionPane.showMessageDialog(this, details, "Rekord részletei", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Ha nincs kivalasztott sor, megjelenit egy hibauzenetet
            JOptionPane.showMessageDialog(this, "Válasszon ki egy rekordot a részletek megtekintéséhez.");
        }
    }

    // Exportalja a telefonkonyv adatait CSV fajlba.
    public void exportToCSV(String filename) {
        // Ellenorzi, hogy a fajlnev nem null es nem ures
        if (filename == null || filename.isEmpty()) {
            filename = "default";
        }

        // Hozzaadja a .csv kiterjesztest, ha meg nincs
        if (!filename.toLowerCase().endsWith(".csv")) {
            filename += ".csv";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Fejlec irasa
            writer.println("Név;Foglalkozás;Telefonszám");

            // Adatok rendezese nev szerint
            List<Szemely> szemelyek = telefonkonyvKezelo.getTelefonkonyv();
            Collections.sort(szemelyek, Comparator.comparing(Szemely::getNev));

            // Adatok irasa
            for (Szemely szemely : szemelyek) {
                writer.printf("%s;%s;%s%n", szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam());
            }
            // Sikeres exportalas uzenet
            JOptionPane.showMessageDialog(this, "Exportálás sikeresen megtörtént.");
        } catch (IOException e) {
            // Hiba uzenet az exportalas soran
            JOptionPane.showMessageDialog(this, "Hiba történt az exportálás során: " + e.getMessage());
        }
    }

    // Importalja a telefonkonyv adatait egy CSV fajlbol.
    public void importFromCSV(String filename) {
        // Ellenorzi, hogy a fajlnev nem null es nem ures
        if (filename == null || filename.isEmpty()) {
            filename = "default";
        }

        // Hozzaadja a .csv kiterjesztest, ha meg nincs
        if (!filename.toLowerCase().endsWith(".csv")) {
            filename += ".csv";
        }

        // Torli a telefonkonyvKezelo meglevo bejegyzeseit
        telefonkonyvKezelo.clearTelefonkonyv();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Fejlec sor kihagyasa
            reader.readLine();

            // Adatsorok olvasasa
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String nev = parts[0];
                    String foglalkozas = parts[1];
                    String telefonszam = parts[2];
                    telefonkonyvKezelo.addSzemely(new Szemely(nev, foglalkozas, telefonszam));
                }
            }

            // Adatok betoltese a tablaba
            loadTableData();
            // Sikeres importalas uzenet
            JOptionPane.showMessageDialog(this, "Importálás sikeresen megtörtént.");
        } catch (IOException e) {
            // Hiba uzenet az importalas soran
            JOptionPane.showMessageDialog(this, "Hiba történt az importálás során: " + e.getMessage());
        }
    }

    // Visszaadja a tabla objektumot.
    public JTable getTabla() {
        return tabla;
    }

    public static void main(String[] args) {
        // Event Dispatch Thread-ben futtatas
        SwingUtilities.invokeLater(() -> {
            // Letrehozza a Telefonkonyv ablakot
            Telefonkonyv frame = new Telefonkonyv();
            // Lathatova teszi az ablakot
            frame.setVisible(true);
        });
    }
}

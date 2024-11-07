package nagyhazi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Import JUnit classes
// import org.junit.runner.JUnitCore;
// import org.junit.runner.Result;
// import org.junit.runner.notification.Failure;


public class Telefonkonyv extends JFrame {
    private TelefonkonyvKezelo telefonkonyvKezelo;
    private JTable tabla;
    private DefaultTableModel tablaModel;

    public Telefonkonyv() {

        // Show selection dialog
        SelectionDialog selectionDialog = new SelectionDialog(this);
        selectionDialog.setVisible(true);

        if (selectionDialog.isRunPhonebook()) {
            // Proceed with phonebook functionality
            initializePhonebook();
        } else if (selectionDialog.isRunTest()) {
            // Run tests and exit
            // runTests();
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    private void initializePhonebook() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        telefonkonyvKezelo = new TelefonkonyvKezelo();

        // Add sample data
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "123456789101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Nagy Anna", "Tanár", "987654321101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Szabó Péter", "Orvos", "555555555101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Tóth Gábor", "Számítógépes Mérnök", "222333444101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Kiss Mária", "Ügyvéd", "444555666101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Farkas László", "Építész", "777888999101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Molnár Éva", "Marketing Szakértő", "111222333101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Papp Zoltán", "Képzőművész", "333444555101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Varga Balázs", "Mérnök", "666777888101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Horváth Klára", "Újságíró", "999000111101"));

        setTitle("Telefonkönyv");
        setSize(1000, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Create the table model and table
        tablaModel = new DefaultTableModel(new Object[] { "Név", "Foglalkozás", "Telefonszám" }, 0);
        tabla = new JTable(tablaModel);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Load initial data into the table
        loadTableData();

        // Create the button panel at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 7, 5, 5));

        JButton ujRekordButton = createStyledButton("Új rekord létrehozása");
        JButton modositButton = createStyledButton("Módosítás");
        JButton torlesButton = createStyledButton("Törlés");
        JButton keresButton = createStyledButton("Keresés");
        JButton detailsButton = createStyledButton("Rekord részletei");
        JButton importButton = createStyledButton("Adatok importálása");
        JButton mentesButton = createStyledButton("Adatbázis fájlba mentése");

        // Add action listeners for buttons
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

        buttonPanel.add(ujRekordButton);
        buttonPanel.add(modositButton);
        buttonPanel.add(torlesButton);
        buttonPanel.add(keresButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(importButton);
        buttonPanel.add(mentesButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 150, 250)); // Kék háttér
        button.setFocusPainted(false);
        return button;
    }

    private void loadTableData() {
        tablaModel.setRowCount(0); // Törli a meglévő sorokat
        for (Szemely szemely : telefonkonyvKezelo.getTelefonkonyv()) {
            tablaModel.addRow(new Object[] { szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam() });
        }
    }

    private boolean validateInput(String nev, String foglalkozas, String telefonszam) {
        return !nev.isEmpty() && !foglalkozas.isEmpty() && telefonszam.matches("\\d{11}");
    }

    public void ujRekord() {
        JTextField nevField = new JTextField();
        JTextField foglalkozasField = new JTextField();
        JTextField telefonszamField = new JTextField();
        Object[] inputFields = {
                "Név:", nevField,
                "Foglalkozás:", foglalkozasField,
                "Telefonszám:", telefonszamField
        };

        int option = JOptionPane.showConfirmDialog(this, inputFields, "Új rekord hozzáadása",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nev = nevField.getText().trim();
            String foglalkozas = foglalkozasField.getText().trim();
            String telefonszam = telefonszamField.getText().trim();

            if (validateInput(nev, foglalkozas, telefonszam)) {
                Szemely ujSzemely = new Szemely(nev, foglalkozas, telefonszam);
                telefonkonyvKezelo.addSzemely(ujSzemely);
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Kérlek, ellenőrizd a megadott adatokat.");
            }
        }
    }

    public void modositRekord() {
        int kivalasztottSor = tabla.getSelectedRow();
        if (kivalasztottSor != -1) {
            JTextField nevField = new JTextField((String) tablaModel.getValueAt(kivalasztottSor, 0));
            JTextField foglalkozasField = new JTextField((String) tablaModel.getValueAt(kivalasztottSor, 1));
            JTextField telefonszamField = new JTextField((String) tablaModel.getValueAt(kivalasztottSor, 2));
            Object[] inputFields = {
                    "Név:", nevField,
                    "Foglalkozás:", foglalkozasField,
                    "Telefonszám:", telefonszamField
            };

            int option = JOptionPane.showConfirmDialog(this, inputFields, "Rekord módosítása",
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nev = nevField.getText().trim();
                String foglalkozas = foglalkozasField.getText().trim();
                String telefonszam = telefonszamField.getText().trim();

                if (validateInput(nev, foglalkozas, telefonszam)) {
                    Szemely modositottSzemely = new Szemely(nev, foglalkozas, telefonszam);
                    telefonkonyvKezelo.modifySzemely(kivalasztottSor, modositottSzemely);
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(this, "Kérlek, ellenőrizd a megadott adatokat.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Válasszon ki egy rekordot a módosításhoz.");
        }
    }

    public void torolRekord() {
        int kivalasztottSor = tabla.getSelectedRow();
        if (kivalasztottSor != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Biztosan törölni szeretnéd ezt a rekordot?",
                    "Megerősítés", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                telefonkonyvKezelo.removeSzemely(kivalasztottSor);
                loadTableData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Válasszon ki egy rekordot a törléshez.");
        }
    }

    public void keresRekord() {
        String keresettSzoveg = JOptionPane.showInputDialog(this, "Adja meg a keresett szót:");
        if (keresettSzoveg != null && !keresettSzoveg.isEmpty()) {
            ArrayList<Szemely> talalatok = telefonkonyvKezelo.search(keresettSzoveg);
            tablaModel.setRowCount(0);
            for (Szemely szemely : talalatok) {
                tablaModel
                        .addRow(new Object[] { szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam() });
            }
        }
    }

    private void showRecordDetails() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow != -1) {
            // Get the selected record
            String nev = (String) tablaModel.getValueAt(selectedRow, 0);
            String foglalkozas = (String) tablaModel.getValueAt(selectedRow, 1);
            String telefonszam = (String) tablaModel.getValueAt(selectedRow, 2);

            // Create and show the details dialog
            String details = String.format("Név: %s\nFoglalkozás: %s\nTelefonszám: %s", nev, foglalkozas, telefonszam);
            JOptionPane.showMessageDialog(this, details, "Rekord részletei", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Válasszon ki egy rekordot a részletek megtekintéséhez.");
        }
    }

    public void exportToCSV(String filename) {
        if (!filename.toLowerCase().endsWith(".csv")) {
            filename += ".csv";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("Név;Foglalkozás;Telefonszám");

            // Sort data by name
            List<Szemely> szemelyek = telefonkonyvKezelo.getTelefonkonyv();
            Collections.sort(szemelyek, Comparator.comparing(Szemely::getNev));

            // Write data
            for (Szemely szemely : szemelyek) {
                writer.printf("%s;%s;%s%n", szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam());
            }

            JOptionPane.showMessageDialog(this, "Exportálás sikeresen megtörtént.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Hiba történt az exportálás során: " + e.getMessage());
        }
    }

    public void importFromCSV(String filename) {
        if (!filename.toLowerCase().endsWith(".csv")) {
            filename += ".csv";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip header line
            reader.readLine();

            // Read data lines
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String nev = parts[0];
                    String foglalkozas = parts[1];
                    String telefonszam = parts[2];
                    telefonkonyvKezelo.addSzemely(new Szemely(nev, foglalkozas, telefonszam));
                }
            }

            loadTableData();
            JOptionPane.showMessageDialog(this, "Importálás sikeresen megtörtént.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Hiba történt az importálás során: " + e.getMessage());
        }
    }

/*
 * private void runTests() {
 * // Futassuk le a JUnit teszteket programból
 * Result result = JUnitCore.runClasses(TelefonkonyvKezeloTeszt.class);
 * 
 * StringBuilder sb = new StringBuilder();
 * sb.append("Tesztek futtatva: ").append(result.getRunCount()).append("\n");
 * sb.append("Sikertelen tesztek: ").append(result.getFailureCount()).append(
 * "\n");
 * sb.append("Ignore-olt tesztek: ").append(result.getIgnoreCount()).append("\n"
 * );
 * sb.append("Futtatási idő: ").append(result.getRunTime()).append("ms\n\n");
 * 
 * if (!result.wasSuccessful()) {
 * sb.append("Sikertelen tesztek részletei:\n");
 * for (Failure failure : result.getFailures()) {
 * sb.append(failure.toString()).append("\n");
 * }
 * } else {
 * sb.append("Minden teszt sikeresen lefutott!");
 * }
 * 
 * JOptionPane.showMessageDialog(this, sb.toString(), "Teszt Eredmények",
 * JOptionPane.INFORMATION_MESSAGE);
 * }
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Telefonkonyv frame = new Telefonkonyv();
            frame.setVisible(true);
        });
    }
}

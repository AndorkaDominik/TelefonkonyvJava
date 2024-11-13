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

    public Telefonkonyv() {
        initializePhonebook();
    }

    public void initializePhonebook() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        telefonkonyvKezelo = new TelefonkonyvKezelo();

        // Add sample data
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

    public void loadTableData() {
        tablaModel.setRowCount(0); // Törli a meglévő sorokat
        for (Szemely szemely : telefonkonyvKezelo.getTelefonkonyv()) {
            tablaModel.addRow(new Object[] { szemely.getNev(), szemely.getFoglalkozas(), szemely.getTelefonszam() });
        }
    }

    public boolean validateInput(String nev, String foglalkozas, String telefonszam) {
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

    public void showRecordDetails() {
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
        if(filename==null || filename.isEmpty()) {
            filename = "default";
        }

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
        if (filename == null || filename.isEmpty()) {
            filename = "default";
        }

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

    public JTable getTabla() {
        return tabla;
    }

    public static void main(String[] args) {
        // Event Dispatch Thread-ben futtatás
        SwingUtilities.invokeLater(() -> {
            // Létrehozza a Telefonkonyv ablakot.
            Telefonkonyv frame = new Telefonkonyv();
            // Láthatóvá teszi az ablakot.
            frame.setVisible(true);
        });
    }
}

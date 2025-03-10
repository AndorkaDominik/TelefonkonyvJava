package nagyhazi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TelefonkonyvTest {

    private Telefonkonyv telefonkonyv;
    private TelefonkonyvKezelo telefonkonyvKezelo;
    private Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");

    @Before
    public void setUp() {
        telefonkonyv = new Telefonkonyv();
        telefonkonyvKezelo = telefonkonyv.telefonkonyvKezelo;
    }

    // Teszteli, hogy egy uj szemely hozzaadasa mukodik
    @Test
    public void testAddSzemely() {

        telefonkonyvKezelo.addSzemely(newPerson);

        assertEquals(11, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Test János", telefonkonyvKezelo.getTelefonkonyv().get(10).getNev());
    }

    // Teszteli, hogy egy szemely modositasa mukodik
    @Test
    public void testModifySzemely() {
        telefonkonyvKezelo.addSzemely(newPerson);
        Szemely modifiedPerson = new Szemely("Test János Modified", "Senior Programozó", "12345678901");

        telefonkonyvKezelo.modifySzemely(0, modifiedPerson);

        assertEquals("Test János Modified", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
        assertEquals("Senior Programozó", telefonkonyvKezelo.getTelefonkonyv().get(0).getFoglalkozas());
    }

    // Teszteli, hogy egy szemely torlese mukodik
    @Test
    public void testRemoveSzemely() {
        telefonkonyvKezelo.addSzemely(newPerson);

        telefonkonyvKezelo.removeSzemely(0);

        assertEquals(10, telefonkonyvKezelo.getTelefonkonyv().size());
    }

    // Teszteli, hogy a keresesi funkcio mukodik
    @Test
    public void testSearch() {
        Szemely person1 = new Szemely("Test Juliska", "Programozó", "12345678901");
        Szemely person2 = new Szemely("Anna Nagy", "Tanár", "98765432110");
        telefonkonyvKezelo.addSzemely(person1);
        telefonkonyvKezelo.addSzemely(person2);

        List<Szemely> results = telefonkonyvKezelo.search("Juliska");

        assertEquals(1, results.size());
        assertEquals("Test Juliska", results.get(0).getNev());
    }

    // Teszteli, hogy az adatok exportalasa CSV fajlba mukodik
    @Test
    public void testExportToCSV() throws IOException {
        Szemely person2 = new Szemely("Anna Nagy", "Tanár", "98765432110");
        telefonkonyvKezelo.addSzemely(newPerson);
        telefonkonyvKezelo.addSzemely(person2);

        String filename = "test_export.csv";

        telefonkonyv.exportToCSV(filename);

        File file = new File(filename);
        assertTrue(file.exists());

        file.delete();
    }

    // Teszteli, hogy az adatok importalasa CSV fajlbol mukodik
    @Test
    public void testImportFromCSV() throws IOException {
        String filename = "test_import.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Név;Foglalkozás;Telefonszám");
            writer.println("Test János;Programozó;12345678901");
            writer.println("Anna Nagy;Tanár;98765432110");
        }

        telefonkonyv.importFromCSV(filename);

        assertEquals(2, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Test János", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
        assertEquals("Anna Nagy", telefonkonyvKezelo.getTelefonkonyv().get(1).getNev());

        File file = new File(filename);
        assertTrue(file.exists());

        file.delete();
    }

    // Teszteli, hogy a bemeneti adatok validalasa mukodik
    @Test
    public void testValidateInput() {
        assertTrue(telefonkonyv.validateInput("John Doe", "Programozó", "12345678901"));
        assertFalse(telefonkonyv.validateInput("John Doe", "Programozó", "1234567890"));
        assertFalse(telefonkonyv.validateInput("", "Programozó", "12345678901"));
        assertFalse(telefonkonyv.validateInput("John Doe", "", "12345678901"));
    }

    // Teszteli, hogy az adatok betoltese a tablaba mukodik
    @Test
    public void testTableData() {
        telefonkonyvKezelo.addSzemely(newPerson);

        telefonkonyv.loadTableData();

        JTable table = telefonkonyv.getTabla();
        assertEquals("Test János", table.getValueAt(10, 0));
        assertEquals("Programozó", table.getValueAt(10, 1));
        assertEquals("12345678901", table.getValueAt(10, 2));
    }

    // Teszteli, hogy az ures vagy null fajlnevek kezelese mukodik
    @Test
    public void test_handle_empty_or_null_file_paths() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.initializePhonebook();

        telefonkonyv.exportToCSV(null);
        telefonkonyv.exportToCSV("");

        telefonkonyv.importFromCSV(null);
        telefonkonyv.importFromCSV("");

        DefaultTableModel model = (DefaultTableModel) telefonkonyv.getTabla().getModel();

        assertEquals(10, model.getRowCount());

        File file = new File("default.csv");
        assertTrue(file.exists());

        file.delete();
    }

    // Teszteli, hogy a telefonkonyv inicializalasa mintaadatokkal mukodik
    @Test
    public void test_initialize_with_sample_data() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.initializePhonebook();

        DefaultTableModel model = (DefaultTableModel) telefonkonyv.getTabla().getModel();
        assertEquals(10, model.getRowCount());
        assertEquals("Kovács János", model.getValueAt(0, 0));
        assertEquals("Programozó", model.getValueAt(0, 1));
        assertEquals("12345678910", model.getValueAt(0, 2));
    }

    // Teszteli, hogy az adatok exportalasa CSV fajlba mukodik (masodik teszt)
    @Test
    public void testExportToCSV2() throws IOException {
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "123456789101"));

        File file = new File("testExport.csv");

        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.telefonkonyvKezelo = telefonkonyvKezelo;
        telefonkonyv.exportToCSV(file.getAbsolutePath());

        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.startsWith("Név;Foglalkozás;Telefonszám"));
        }

        // Fájl törlése a teszt végén
        file.delete();
    }

    // Teszteli, hogy az adatok importalasa CSV fajlbol mukodik (masodik teszt)
    @Test
    public void testImportFromCSV2() throws IOException {
        String csvData = "Név;Foglalkozás;Telefonszám\nKovács János;Programozó;123456789101\n";
        File file = new File("testImport.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(csvData);
        }
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.telefonkonyvKezelo = telefonkonyvKezelo;
        telefonkonyv.importFromCSV(file.getAbsolutePath());

        assertEquals(1, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Kovács János", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());

        // Fájl törlése a teszt végén
        file.delete();
    }

    // Teszteli, hogy a telefonkonyv UI komponenseinek inicializalasa mukodik
    @Test
    public void test_initialize_telefonkonyv_ui_components() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        assertNotNull(telefonkonyv.getTabla());
        assertEquals(10, telefonkonyv.getTabla().getRowCount());
        assertEquals("Telefonkönyv", telefonkonyv.getTitle());
        assertEquals(1150, telefonkonyv.getWidth());
        assertEquals(500, telefonkonyv.getHeight());
    }

    // Teszteli, hogy a tabla oszlopfejlecei helyesen vannak beallitva
    @Test
    public void test_table_column_headers() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        JTable table = telefonkonyv.getTabla();
        assertNotNull(table);
        assertEquals("Név", table.getColumnName(0));
        assertEquals("Foglalkozás", table.getColumnName(1));
        assertEquals("Telefonszám", table.getColumnName(2));
    }

    // Teszteli, hogy egy uj rekord hozzaadasa mukodik
    @Test
    public void test_add_new_record() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        int initialRowCount = telefonkonyv.getTabla().getRowCount();

        telefonkonyv.telefonkonyvKezelo.addSzemely(new Szemely("Teszt Elek", "Tesztelő", "12345678901"));
        telefonkonyv.loadTableData();

        assertEquals(initialRowCount + 1, telefonkonyv.getTabla().getRowCount());
        assertEquals("Teszt Elek", telefonkonyv.getTabla().getValueAt(initialRowCount, 0));
        assertEquals("Tesztelő", telefonkonyv.getTabla().getValueAt(initialRowCount, 1));
        assertEquals("12345678901", telefonkonyv.getTabla().getValueAt(initialRowCount, 2));
    }
}

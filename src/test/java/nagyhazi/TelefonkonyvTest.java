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

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TelefonkonyvTest {

    private Telefonkonyv telefonkonyv;
    private TelefonkonyvKezelo telefonkonyvKezelo;

    @Before
    public void setUp() {
        telefonkonyv = new Telefonkonyv();
        telefonkonyvKezelo = telefonkonyv.telefonkonyvKezelo;
    }

    @Test
    public void testAddSzemely() {
        Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");

        telefonkonyvKezelo.addSzemely(newPerson);

        assertEquals(11, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Test János", telefonkonyvKezelo.getTelefonkonyv().get(10).getNev());
    }

    @Test
    public void testModifySzemely() {
        Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");
        telefonkonyvKezelo.addSzemely(newPerson);
        Szemely modifiedPerson = new Szemely("Test János Modified", "Senior Programozó", "12345678901");

        telefonkonyvKezelo.modifySzemely(0, modifiedPerson);

        assertEquals("Test János Modified", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
        assertEquals("Senior Programozó", telefonkonyvKezelo.getTelefonkonyv().get(0).getFoglalkozas());
    }

    @Test
    public void testRemoveSzemely() {
        Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");
        telefonkonyvKezelo.addSzemely(newPerson);

        telefonkonyvKezelo.removeSzemely(0);

        assertEquals(10, telefonkonyvKezelo.getTelefonkonyv().size());
    }

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

    @Test
    public void testExportToCSV() throws IOException {
        Szemely person1 = new Szemely("Test János", "Programozó", "12345678901");
        Szemely person2 = new Szemely("Anna Nagy", "Tanár", "98765432110");
        telefonkonyvKezelo.addSzemely(person1);
        telefonkonyvKezelo.addSzemely(person2);

        String filename = "test_export.csv";

        telefonkonyv.exportToCSV(filename);

        File file = new File(filename);
        assertTrue(file.exists());

        file.delete();
    }

    @Test
    public void testImportFromCSV() throws IOException {
        String filename = "test_import.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Név;Foglalkozás;Telefonszám");
            writer.println("Test János;Programozó;12345678901");
            writer.println("Anna Nagy;Tanár;98765432110");
        }

        telefonkonyv.importFromCSV(filename);

        assertEquals(12, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Test János", telefonkonyvKezelo.getTelefonkonyv().get(10).getNev());
        assertEquals("Anna Nagy", telefonkonyvKezelo.getTelefonkonyv().get(11).getNev());

        new File(filename).delete();
    }

    @Test
    public void testValidateInput() {
        // Test valid inputs
        assertTrue(telefonkonyv.validateInput("John Doe", "Programozó", "12345678901"));

        // Test invalid phone number
        assertFalse(telefonkonyv.validateInput("John Doe", "Programozó", "1234567890"));

        // Test empty name
        assertFalse(telefonkonyv.validateInput("", "Programozó", "12345678901"));

        // Test empty occupation
        assertFalse(telefonkonyv.validateInput("John Doe", "", "12345678901"));
    }

    @Test
    public void testTableData() {
        Szemely person1 = new Szemely("Test János", "Programozó", "12345678901");
        telefonkonyvKezelo.addSzemely(person1);

        telefonkonyv.loadTableData();

        JTable table = telefonkonyv.getTabla();
        assertEquals("Test János", table.getValueAt(10, 0));
        assertEquals("Programozó", table.getValueAt(10, 1));
        assertEquals("12345678901", table.getValueAt(10, 2));
    }

    @Test
    public void test_handle_empty_or_null_file_paths() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.initializePhonebook();

        telefonkonyv.importFromCSV(null);
        telefonkonyv.importFromCSV("");

        telefonkonyv.exportToCSV(null);
        telefonkonyv.exportToCSV("");

        // Assuming no exception is thrown and no changes occur
        DefaultTableModel model = (DefaultTableModel) telefonkonyv.getTabla().getModel();



        assertEquals(10, model.getRowCount());

        // Then
        File file = new File("default.csv");
        assertTrue(file.exists());

        // Clean up
        file.delete();
    }

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

    
    @Test
    public void testExportToCSV2() throws IOException {
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó",
        "123456789101"));
        
        File tempFile = File.createTempFile("testExport", ".csv");
        
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.telefonkonyvKezelo = telefonkonyvKezelo;
        telefonkonyv.exportToCSV(tempFile.getAbsolutePath());
        
        assertTrue(tempFile.exists());
        // Check the content of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.startsWith("Név;Foglalkozás;Telefonszám"));
        }
    }
    
    @Test
    public void testImportFromCSV2() throws IOException {
        String csvData =
        "Név;Foglalkozás;Telefonszám\nKovács János;Programozó;123456789101\n";
        File tempFile = File.createTempFile("testImport", ".csv");
        tempFile.deleteOnExit();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(csvData);
        }
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.telefonkonyvKezelo = telefonkonyvKezelo;
        telefonkonyv.importFromCSV(tempFile.getAbsolutePath());
        
        assertEquals(1, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Kovács János",
        telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
    }


    @Test
    public void test_initialize_telefonkonyv_ui_components() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        assertNotNull(telefonkonyv.getTabla());
        assertEquals(10, telefonkonyv.getTabla().getRowCount());
        assertEquals("Telefonkönyv", telefonkonyv.getTitle());
        assertEquals(1000, telefonkonyv.getWidth());
        assertEquals(500, telefonkonyv.getHeight());
    }

    @Test
    public void test_jframe_title_is_set_correctly() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        assertEquals("Telefonkönyv", telefonkonyv.getTitle());
    }

    @Test
    public void test_table_column_headers() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        JTable table = telefonkonyv.getTabla();
        assertNotNull(table);
        assertEquals("Név", table.getColumnName(0));
        assertEquals("Foglalkozás", table.getColumnName(1));
        assertEquals("Telefonszám", table.getColumnName(2));
    }

    /*
    @Test
    public void test_buttons_visibility_and_labels() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        telefonkonyv.setVisible(true);

        JPanel buttonPanel = (JPanel) telefonkonyv.getContentPane().getComponent(1);
        assertNotNull(buttonPanel);
        assertEquals(7, buttonPanel.getComponentCount());

        JButton ujRekordButton = (JButton) buttonPanel.getComponent(0);
        JButton modositButton = (JButton) buttonPanel.getComponent(1);
        JButton torlesButton = (JButton) buttonPanel.getComponent(2);
        JButton keresButton = (JButton) buttonPanel.getComponent(3);
        JButton detailsButton = (JButton) buttonPanel.getComponent(4);
        JButton importButton = (JButton) buttonPanel.getComponent(5);
        JButton mentesButton = (JButton) buttonPanel.getComponent(6);

        assertEquals("Új rekord létrehozása", ujRekordButton.getText());
        assertEquals("Módosítás", modositButton.getText());
        assertEquals("Törlés", torlesButton.getText());
        assertEquals("Keresés", keresButton.getText());
        assertEquals("Rekord részletei", detailsButton.getText());
        assertEquals("Adatok importálása", importButton.getText());
        assertEquals("Adatbázis fájlba mentése", mentesButton.getText());
    }
    */
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

    /*
    @Test
    public void test_uj_rekord_button_opens_input_dialog() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        JButton ujRekordButton = (JButton) telefonkonyv.getContentPane().getComponent(1).getComponent(0);
    
        ujRekordButton.doClick();
    
        String expectedTitle = "Új rekord hozzáadása";
        String actualTitle = JOptionPane.getRootFrame().getTitle();
    
        assertEquals(expectedTitle, actualTitle);
    }
    */

    @Test
    public void test_add_new_record1() {
        Telefonkonyv telefonkonyv = new Telefonkonyv();
        int initialRowCount = telefonkonyv.getTabla().getRowCount();

        // Simulate adding a new record
        telefonkonyv.telefonkonyvKezelo.addSzemely(new Szemely("Teszt Elek", "Fejlesztő", "12345678901"));
        telefonkonyv.loadTableData();

        // Verify the new record is added
        assertEquals(initialRowCount + 1, telefonkonyv.getTabla().getRowCount());
        assertEquals("Teszt Elek", telefonkonyv.getTabla().getValueAt(initialRowCount, 0));
        assertEquals("Fejlesztő", telefonkonyv.getTabla().getValueAt(initialRowCount, 1));
        assertEquals("12345678901", telefonkonyv.getTabla().getValueAt(initialRowCount, 2));
    }

}

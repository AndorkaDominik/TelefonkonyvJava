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

import org.junit.After;
import org.junit.Before;
import org.junit.Before;
import org.junit.Test;

public class TelefonkonyvTest {

    private Telefonkonyv telefonkonyv;
    private TelefonkonyvKezelo telefonkonyvKezelo;

    @Before
    public void setUp() {
        // Initialize the phonebook
        telefonkonyv = new Telefonkonyv();
        telefonkonyvKezelo = telefonkonyv.telefonkonyvKezelo;
    }

    @Test
    public void testAddSzemely() {
        // Given
        Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");

        // When
        telefonkonyvKezelo.addSzemely(newPerson);

        // Then
        assertEquals(11, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Test János", telefonkonyvKezelo.getTelefonkonyv().get(10).getNev());
    }

    @Test
    public void testModifySzemely() {
        // Given
        Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");
        telefonkonyvKezelo.addSzemely(newPerson);
        Szemely modifiedPerson = new Szemely("Test János Modified", "Senior Programozó", "12345678901");

        // When
        telefonkonyvKezelo.modifySzemely(0, modifiedPerson);

        // Then
        assertEquals("Test János Modified", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
        assertEquals("Senior Programozó", telefonkonyvKezelo.getTelefonkonyv().get(0).getFoglalkozas());
    }

    @Test
    public void testRemoveSzemely() {
        // Given
        Szemely newPerson = new Szemely("Test János", "Programozó", "12345678901");
        telefonkonyvKezelo.addSzemely(newPerson);

        // When
        telefonkonyvKezelo.removeSzemely(0);

        // Then
        assertEquals(10, telefonkonyvKezelo.getTelefonkonyv().size());
    }

    @Test
    public void testSearch() {
        // Given
        Szemely person1 = new Szemely("Test Juliska", "Programozó", "12345678901");
        Szemely person2 = new Szemely("Anna Nagy", "Tanár", "98765432110");
        telefonkonyvKezelo.addSzemely(person1);
        telefonkonyvKezelo.addSzemely(person2);

        // When
        List<Szemely> results = telefonkonyvKezelo.search("Juliska");

        // Then
        assertEquals(1, results.size());
        assertEquals("Test Juliska", results.get(0).getNev());
    }

    @Test
    public void testExportToCSV() throws IOException {
        // Given
        Szemely person1 = new Szemely("Test János", "Programozó", "12345678901");
        Szemely person2 = new Szemely("Anna Nagy", "Tanár", "98765432110");
        telefonkonyvKezelo.addSzemely(person1);
        telefonkonyvKezelo.addSzemely(person2);

        // File path
        String filename = "test_export.csv";

        // When
        telefonkonyv.exportToCSV(filename);

        // Then
        File file = new File(filename);
        assertTrue(file.exists());

        // Clean up
        file.delete();
    }

    @Test
    public void testImportFromCSV() throws IOException {
        // Given
        String filename = "test_import.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Név;Foglalkozás;Telefonszám");
            writer.println("Test János;Programozó;12345678901");
            writer.println("Anna Nagy;Tanár;98765432110");
        }

        // When
        telefonkonyv.importFromCSV(filename);

        // Then
        assertEquals(12, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Test János", telefonkonyvKezelo.getTelefonkonyv().get(10).getNev());
        assertEquals("Anna Nagy", telefonkonyvKezelo.getTelefonkonyv().get(11).getNev());

        // Clean up
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
        // Given
        Szemely person1 = new Szemely("Test János", "Programozó", "12345678901");
        telefonkonyvKezelo.addSzemely(person1);

        // When
        telefonkonyv.loadTableData();

        // Then
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
}

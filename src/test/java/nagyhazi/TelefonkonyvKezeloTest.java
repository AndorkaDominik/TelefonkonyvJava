package nagyhazi;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TelefonkonyvKezeloTest {

    private TelefonkonyvKezelo telefonkonyvKezelo;
    private String testFilename = "test_telefonkonyv.dat";

    @Before
    public void setUp() {
        telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "123456789101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Nagy Anna", "Tanár", "987654321101"));
    }

    @Test
    public void testConstructorInitialization() {
        // Check that telefonkonyvKezelo is not null
        assertNotNull("telefonkonyvKezelo should be initialized", telefonkonyvKezelo);

        // Check that the initial list of Szemely objects is empty
        assertEquals("Initial size of Szemely list should be 2", 2, telefonkonyvKezelo.getTelefonkonyv().size());
    }

    @Test
    public void testAddSzemely() {
        Szemely ujSzemely = new Szemely("Tóth Gábor", "Mérnök", "555555555101");
        telefonkonyvKezelo.addSzemely(ujSzemely);

        assertEquals(3, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Tóth Gábor", telefonkonyvKezelo.getTelefonkonyv().get(2).getNev());
    }

    @Test
    public void testModifySzemely() {
        Szemely modositottSzemely = new Szemely("Nagy Anna", "Fizikus", "987654321101");
        telefonkonyvKezelo.modifySzemely(1, modositottSzemely);

        assertEquals("Fizikus", telefonkonyvKezelo.getTelefonkonyv().get(1).getFoglalkozas());
    }

    @Test
    public void testRemoveSzemely() {
        telefonkonyvKezelo.removeSzemely(0);
        assertEquals(1, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Nagy Anna", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
    }

    @Test
    public void testSearch() {
        ArrayList<Szemely> talalatok = telefonkonyvKezelo.search("Anna");
        assertEquals(1, talalatok.size());
        assertEquals("Nagy Anna", talalatok.get(0).getNev());

        talalatok = telefonkonyvKezelo.search("Tanár");
        assertEquals(1, talalatok.size());
    }

    @Test
    public void testGetTelefonkonyv() {
        assertNotNull("getTelefonkonyv should return a non-null list", telefonkonyvKezelo.getTelefonkonyv());

        assertEquals("Initial size of telefonkonyv list should be 2", 2, telefonkonyvKezelo.getTelefonkonyv().size());

        Szemely szemely = new Szemely("Kovács János", "Programozó", "123456789101");
        telefonkonyvKezelo.addSzemely(szemely);
        ArrayList<Szemely> telefonkonyvList = telefonkonyvKezelo.getTelefonkonyv();

        assertEquals("Size of telefonkonyv list should be 1 after adding a Szemely", 3, telefonkonyvList.size());
        assertTrue("telefonkonyv list should contain the added Szemely", telefonkonyvList.contains(szemely));
    }

    @Test
    public void testSaveToFile() throws IOException {
        // Save the current state to a file
        telefonkonyvKezelo.saveToFile(testFilename);

        // Check that the file exists after saving
        File file = new File(testFilename);
        assertTrue("File should exist after saving", file.exists());

        // Optionally, check the file size to ensure data was written (not empty)
        assertTrue("File should not be empty after saving", file.length() > 0);

        // Clean up by deleting the file
        file.delete();
    }

    @Test
    public void testLoadFromFile() throws IOException, ClassNotFoundException {
        // First, save the current state to a file
        telefonkonyvKezelo.saveToFile(testFilename);

        // Create a new TelefonkonyvKezelo instance and load from the saved file
        TelefonkonyvKezelo ujTelefonkonyvKezelo = new TelefonkonyvKezelo();
        ujTelefonkonyvKezelo.loadFromFile(testFilename);

        // Verify that the loaded data matches the original
        assertEquals("Size of telefonkonyv list should be 2 after loading", 2,
                ujTelefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("First Szemely name should match after loading", "Kovács János",
                ujTelefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
        assertEquals("Second Szemely name should match after loading", "Nagy Anna",
                ujTelefonkonyvKezelo.getTelefonkonyv().get(1).getNev());

        // Clean up by deleting the file
        File file = new File(testFilename);
        file.delete();
    }

    @Test
    public void testSaveToFileAndLoadFromFile() throws IOException, ClassNotFoundException {
        String testFilename = "test_telefonkonyv.dat";

        telefonkonyvKezelo.saveToFile(testFilename);
        TelefonkonyvKezelo ujTelefonkonyvKezelo = new TelefonkonyvKezelo();
        ujTelefonkonyvKezelo.loadFromFile(testFilename);

        assertEquals(2, ujTelefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Kovács János", ujTelefonkonyvKezelo.getTelefonkonyv().get(0).getNev());

        // Delete the test file after use
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }
}

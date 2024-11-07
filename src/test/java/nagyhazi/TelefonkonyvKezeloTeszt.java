package nagyhazi;

// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TelefonkonyvKezeloTeszt {

    private TelefonkonyvKezelo telefonkonyvKezelo;

    @Before
    public void setUp() {
        telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "123456789101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Nagy Anna", "Tanár", "987654321101"));
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

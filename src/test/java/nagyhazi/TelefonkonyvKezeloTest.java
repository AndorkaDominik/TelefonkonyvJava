package nagyhazi;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TelefonkonyvKezeloTest {

    private TelefonkonyvKezelo telefonkonyvKezelo;

    @Before
    public void setUp() {
        telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "123456789101"));
        telefonkonyvKezelo.addSzemely(new Szemely("Nagy Anna", "Tanár", "987654321101"));
    }

    // Teszteli, hogy a konstruktor helyesen inicializalja az objektumot
    @Test
    public void testConstructorInitialization() {
        assertNotNull("telefonkonyvKezelo should be initialized", telefonkonyvKezelo);

        assertEquals("Initial size of Szemely list should be 2", 2, telefonkonyvKezelo.getTelefonkonyv().size());
    }

    // Teszteli, hogy a szemely hozzaadasa mukodik
    @Test
    public void testAddSzemely() {
        Szemely ujSzemely = new Szemely("Tóth Gábor", "Mérnök", "555555555101");
        telefonkonyvKezelo.addSzemely(ujSzemely);

        assertEquals(3, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Tóth Gábor", telefonkonyvKezelo.getTelefonkonyv().get(2).getNev());
    }

    // Teszteli, hogy a szemely modositasa mukodik
    @Test
    public void testModifySzemely() {
        Szemely modositottSzemely = new Szemely("Nagy Anna", "Fizikus", "987654321101");
        telefonkonyvKezelo.modifySzemely(1, modositottSzemely);

        assertEquals("Fizikus", telefonkonyvKezelo.getTelefonkonyv().get(1).getFoglalkozas());
    }

    // Teszteli, hogy a szemely torlese mukodik
    @Test
    public void testRemoveSzemely() {
        telefonkonyvKezelo.removeSzemely(0);
        assertEquals(1, telefonkonyvKezelo.getTelefonkonyv().size());
        assertEquals("Nagy Anna", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
    }

    // Teszteli, hogy a keresesi funkcio mukodik
    @Test
    public void testSearch() {
        ArrayList<Szemely> talalatok = telefonkonyvKezelo.search("Anna");
        assertEquals(1, talalatok.size());
        assertEquals("Nagy Anna", talalatok.get(0).getNev());

        talalatok = telefonkonyvKezelo.search("Tanár");
        assertEquals(1, talalatok.size());
    }

    // Teszteli, hogy a telefonkonyv visszaadasa mukodik
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
    public void test_clear_non_empty_telefonkonyv() {
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("John Doe", "Kovács", "06202001232"));
        telefonkonyvKezelo.addSzemely(new Szemely("Jane Smith", "Műkörmös", "06202001231"));
        telefonkonyvKezelo.clearTelefonkonyv();
        assertTrue(telefonkonyvKezelo.getTelefonkonyv().isEmpty());
    }

    @Test
    public void test_clear_empty_telefonkonyv() {
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.clearTelefonkonyv();
        assertTrue(telefonkonyvKezelo.getTelefonkonyv().isEmpty());
    }
}

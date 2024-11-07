package nagyhazi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SzemelyTest {
    
    @Test
    public void test_creation_with_valid_values() {
        Szemely szemely = new Szemely("John Doe", "Engineer", "123456789");
        assertEquals("John Doe", szemely.getNev());
        assertEquals("Engineer", szemely.getFoglalkozas());
        assertEquals("123456789", szemely.getTelefonszam());
    }

    @Test
    public void test_creation_with_null_values() {
        Szemely szemely = new Szemely(null, null, null);
        assertNull(szemely.getNev());
        assertNull(szemely.getFoglalkozas());
        assertNull(szemely.getTelefonszam());
    }
}

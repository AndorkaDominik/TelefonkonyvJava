package nagyhazi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SzemelyTest {
    
    // Teszteli, hogy a Szemely objektum helyesen jon letre ervenyes ertekekkel.
    @Test
    public void test_creation_with_valid_values() {
        Szemely szemely = new Szemely("John Doe", "Engineer", "12345678910");
        assertEquals("John Doe", szemely.getNev());
        assertEquals("Engineer", szemely.getFoglalkozas());
        assertEquals("12345678910", szemely.getTelefonszam());
    }

    // Teszteli, hogy a Szemely objektum helyesen jon letre null ertekekkel.
    @Test
    public void test_creation_with_null_values() {
        Szemely szemely = new Szemely(null, null, null);
        assertNull(szemely.getNev());
        assertNull(szemely.getFoglalkozas());
        assertNull(szemely.getTelefonszam());
    }

    // Teszteli a nev beallitasat
    @Test
    public void test_set_nev() {
        Szemely szemely = new Szemely("John Doe", "Engineer", "12345678910");
        szemely.setNev("Jane Doe");
        assertEquals("Jane Doe", szemely.getNev());
    }

    // Teszteli a foglalkozas beallitasat
    @Test
    public void test_set_foglalkozas() {
        Szemely szemely = new Szemely("John Doe", "Engineer", "12345678910");
        szemely.setFoglalkozas("Doctor");
        assertEquals("Doctor", szemely.getFoglalkozas());
    }

    // Teszteli a telefonszam beallitasat
    @Test
    public void test_set_telefonszam() {
        Szemely szemely = new Szemely("John Doe", "Engineer", "12345678910");
        szemely.setTelefonszam("06202720200");
        assertEquals("06202720200", szemely.getTelefonszam());
    }

    // Teszteli a toString metodus formatumat
    @Test
    public void test_to_string_format() {
        Szemely szemely = new Szemely("John Doe", "Engineer", "123456789");
        String expected = "John Doe, Engineer, 123456789";
        assertEquals(expected, szemely.toString());
    }

    // Teszteli a toString metodust null ertekekkel
    @Test
    public void test_to_string_with_null_values() {
        Szemely szemely = new Szemely(null, null, null);
        String expected = "null, null, null";
        assertEquals(expected, szemely.toString());
    }

    // Teszteli a kulonleges karakterek kezeleset a mezokben.
    @Test
    public void test_special_characters_in_fields() {
        Szemely szemely = new Szemely("J@hn Dœ", "Eng!neer", "+1-234-567-890");
        assertEquals("J@hn Dœ", szemely.getNev());
        assertEquals("Eng!neer", szemely.getFoglalkozas());
        assertEquals("+1-234-567-890", szemely.getTelefonszam());
    }
}

package nagyhazi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class TelefonkonyvTest {
    
     @Test
    public void testExportToCSV() throws IOException {
        TelefonkonyvKezelo telefonkonyvKezelo = new TelefonkonyvKezelo();
        telefonkonyvKezelo.addSzemely(new Szemely("Kovács János", "Programozó", "123456789101"));
        
        File tempFile = File.createTempFile("testExport", ".csv");
        tempFile.deleteOnExit(); // Ensure the temp file gets deleted after the test
        
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
    public void testImportFromCSV() throws IOException {
        String csvData = "Név;Foglalkozás;Telefonszám\nKovács János;Programozó;123456789101\n";
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
        assertEquals("Kovács János", telefonkonyvKezelo.getTelefonkonyv().get(0).getNev());
    }
    
}

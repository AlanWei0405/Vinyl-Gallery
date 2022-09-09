package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VinylListTest {
    VinylList vinyls;
    Vinyl folklore = new Vinyl("folklore", 10);

    @BeforeEach
    void setUpANewVinylList() {
        vinyls = new VinylList();
    }

    @AfterEach
    void resetNextVinylID() {
        Vinyl.nextVinylID = 1;
    }

    @Test
    void testAddVinyl() {
        vinyls.addVinyl(folklore);
        assertEquals(1, vinyls.switchToList().size());
        assertTrue(vinyls.switchToList().contains(folklore));
    }

    @Test
    void testRemoveVinyl() {
        vinyls.addVinyl(folklore);
        vinyls.removeVinyl(folklore);
        assertEquals(0, vinyls.switchToList().size());
    }

    @Test
    void testGetVinyl() {
        vinyls.addVinyl(folklore);
        assertEquals(folklore, vinyls.getVinyl(1));
        assertNull(vinyls.getVinyl(2));
        vinyls.removeVinyl(folklore);
        assertNull(vinyls.getVinyl(1));
    }

    @Test
    void testGetColumnName() {
        vinyls.addVinyl(folklore);
        assertEquals("ID", vinyls.getColumnName(0));
    }

    @Test
    void testGetRowCount() {
        vinyls.addVinyl(folklore);
        assertEquals(1, vinyls.getRowCount());
    }

    @Test
    void testGetColumnCount() {
        vinyls.addVinyl(folklore);
        assertEquals(3, vinyls.getColumnCount());
    }

    @Test
    void testGetValueAt() {
        vinyls.addVinyl(folklore);
        try {
            assertNull(vinyls.getValueAt(0, 3));
            fail("Exception not thrown.");
        } catch (IllegalStateException e) {
            assertEquals(1, vinyls.getValueAt(0, 0));
            assertEquals("folklore", vinyls.getValueAt(0, 1));
            assertEquals(10, vinyls.getValueAt(0, 2));
        }

    }
}

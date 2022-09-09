package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VinylTest {
    Vinyl vinylTest;

    @BeforeEach
    void setUpANewVinyl() {
        vinylTest = new Vinyl("folklore", 10);
        vinylTest.resetNextVinylID();
    }

    @Test
    void testConstructor() {
        assertEquals(1, vinylTest.getId());
        assertEquals("folklore", vinylTest.getTitle());
        assertEquals(10, vinylTest.getQuantity());
    }

    @Test
    void testEditVinyl() {
        vinylTest.editVinyl("evermore", 13);
        assertEquals("evermore", vinylTest.getTitle());
        assertEquals(13, vinylTest.getQuantity());
    }
}
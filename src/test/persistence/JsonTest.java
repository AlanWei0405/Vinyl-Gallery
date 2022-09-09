package persistence;

import model.Vinyl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkVinyl(String title, int quantity, Vinyl vinyl) {
        assertEquals(title, vinyl.getTitle());
        assertEquals(quantity, vinyl.getQuantity());
    }
}

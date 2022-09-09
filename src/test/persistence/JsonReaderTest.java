package persistence;

import model.Vinyl;
import model.VinylList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            VinylList vinyls = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyVinylList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyVinylList.json");
        try {
            VinylList vinyls = reader.read();
            assertEquals(0, vinyls.switchToList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralVinylList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralVinylList.json");
        try {
            VinylList vinyls = reader.read();
            List<Vinyl> thingies = vinyls.switchToList();
            assertEquals(2, thingies.size());
            checkVinyl("folklore", 8, vinyls.getVinyl(1));
            checkVinyl("evermore", 9, vinyls.getVinyl(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
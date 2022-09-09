package persistence;

import model.Vinyl;
import model.VinylList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            VinylList vinyls = new VinylList();
            JsonWriter writer = new JsonWriter("./data/this\0is\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyVinylList() {
        try {
            VinylList vinyls = new VinylList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyVinylList.json");
            writer.open();
            writer.write(vinyls);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyVinylList.json");
            vinyls = reader.read();
            assertEquals(0, vinyls.switchToList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralVinylList() {
        try {
            VinylList vinyls = new VinylList();
            Vinyl vinylToWrite1 = new Vinyl("folklore", 8);
            Vinyl vinylToWrite2 = new Vinyl("evermore", 9);
            vinyls.addVinyl(vinylToWrite1);
            vinyls.addVinyl(vinylToWrite2);
            vinylToWrite2.resetNextVinylID();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralVinylList.json");
            writer.open();
            writer.write(vinyls);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralVinylList.json");
            vinyls = reader.read();
            List<Vinyl> gallery = vinyls.switchToList();
            assertEquals(2, gallery.size());
            checkVinyl("folklore", 8, vinyls.getVinyl(1));
            checkVinyl("evermore", 9, vinyls.getVinyl(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
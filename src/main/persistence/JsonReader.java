package persistence;

import model.Vinyl;
import model.VinylList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads VinylList from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads VinylList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VinylList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVinylList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses vinyl list from JSON object and returns it
    private VinylList parseVinylList(JSONObject jsonObject) {
        VinylList vinyls = new VinylList();
        addVinylList(vinyls, jsonObject);
        return vinyls;
    }

    // MODIFIES: vinyls
    // EFFECTS: parses vinyls from JSON object and adds them to vinyl list
    private void addVinylList(VinylList vinyls, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("vinyl gallery");
        for (Object json : jsonArray) {
            JSONObject nextVinyl = (JSONObject) json;
            addVinylObject(vinyls, nextVinyl);
        }
    }

    // MODIFIES: vinyls
    // EFFECTS: parses vinyl from JSON object and adds it to vinyl list
    private void addVinylObject(VinylList vinyls, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int quantity = jsonObject.getInt("quantity");
        Vinyl vinyl = new Vinyl(title, quantity);
        vinyls.addVinyl(vinyl);
    }
}


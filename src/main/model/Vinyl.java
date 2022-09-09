package model;

import org.json.JSONObject;
import persistence.Writable;

//Represent a vinyl containing id, title, and quantity.
public class Vinyl implements Writable {
    static int nextVinylID = 1; //increase by 1 after everytime a vinyl is created so that all vinyl ids are different.
    private final int id;       //Vinyl id
    private String title;       //Vinyl title
    private int quantity;       //Vinyl quantity

    /*
     * REQUIRES: title has a non-zero length and quantity has a non-zero integer
     * EFFECTS: vinyl id is generated;
     *          vinyl title is set to title;
     *          vinyl quantity is set to quantity.
     */
    public Vinyl(String title, int quantity) {
        this.id = nextVinylID++;
        this.title = title;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /*
     * MODIFIES: this
     * EFFECTS: update the title and quantity of a vinyl
     */
    public void editVinyl(String newTitle, int newQuantity) {
        setTitle(newTitle);
        setQuantity(newQuantity);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("quantity", quantity);
        return json;
    }

    public void resetNextVinylID() {
        nextVinylID = 1;
    }
}

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

//Represent a vinyl list containing vinyls.
public class VinylList extends AbstractTableModel implements Writable {
    private List<Vinyl> vinylList;     //Vinyl list
    private final String[] titles = {"ID", "Title", "Quantity"};  //Field names

    /*
     * EFFECTS: create a new list of vinyl
     */
    public VinylList() {
        vinylList = new ArrayList();
    }

    /*
     * REQUIRES: the vinyl object exists
     * EFFECTS: add a vinyl object to the list
     */
    public void addVinyl(Vinyl vinyl) {
        vinylList.add(vinyl);
        EventLog.getInstance().logEvent(new Event("Vinyl added to vinyl list."));
    }

    /*
     * REQUIRES: the vinyl object exists
     * EFFECTS: remove a vinyl object to the list
     */
    public void removeVinyl(Vinyl vinyl) {
        vinylList.remove(vinyl);
        EventLog.getInstance().logEvent(new Event("Vinyl removed from vinyl list."));
    }

    /*
     * REQUIRES: vinylID is a non-zero integer
     * EFFECTS: get the vinyl object with the input vinyl id
     */
    public Vinyl getVinyl(int vinylID) {
        for (Vinyl vinyl : vinylList) {
            if (vinyl.getId() == vinylID) {
                return vinyl;
            }
        }
        return null;
    }

    /*
     * EFFECTS: change type from VinylList to List<Vinyl>
     */
    public List<Vinyl> switchToList() {
        return vinylList;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("vinyl gallery", vinylsToJson());
        return json;
    }

    // EFFECTS: returns vinyls in this vinyl gallery as a JSON array
    private JSONArray vinylsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Vinyl vinyl : vinylList) {
            jsonArray.put(vinyl.toJson());
        }
        return jsonArray;
    }

    @Override
    public String getColumnName(int column) {
        return titles[column];
    }

    @Override
    public int getRowCount() {
        return vinylList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IllegalStateException {
        Vinyl vinyl = vinylList.get(rowIndex);
        Object value;
        switch (columnIndex) {
            case 0:
                value = vinyl.getId();
                break;
            case 1:
                value = vinyl.getTitle();
                break;
            case 2:
                value = vinyl.getQuantity();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + columnIndex);
        }
        return value;
    }
}

package ui;

import model.Vinyl;
import model.VinylList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//Vinyl Gallery application
public class VinylGallery {
    private static final String JSON_STORE = "./data/vinyl gallery.json";
    private VinylList vinylList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Vinyl Gallery application
    public VinylGallery() throws FileNotFoundException {
        runVinylGallery();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runVinylGallery() {
        vinylList = new VinylList();
        boolean keepGoing = true;
        String command;
        String runMode;

        init();

        showRunMode();
        runMode = input.next();
        runMode = runMode.toLowerCase();
        if (runMode.equals("1")) {
            runInGUI();
        } else {
            while (keepGoing) {
                showFunctions();
                command = input.next();
                command = command.toLowerCase();
                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
            System.out.println("\nGoodbye!");
        }
    }

    private void runInGUI() {
        new SplashScreen();
        new ControlCenterFrame(vinylList);
    }

    // MODIFIES: this
    // EFFECTS: initializes input
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays run modes to user
    private void showRunMode() {
        System.out.println("*************");
        System.out.println("Type 1 to run in GUI, other to continue:");
        System.out.println("*************");
    }

    // EFFECTS: displays functions to user
    private void showFunctions() {
        System.out.println("*************");
        System.out.println("Please choose one of the functions below, using the corresponding letter to indicate: ");
        System.out.println("a. Add vinyl(s)");
        System.out.println("b. Remove a vinyl");
        System.out.println("c. Edit a vinyl");
        System.out.println("d. Show current vinyl gallery");
        System.out.println("e. Save vinyl gallery to file");
        System.out.println("f. Load vinyl gallery from file");
        System.out.println("q. Quit");
        System.out.println("*************");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        input.nextLine();
        executeCommand(command);
    }

    // MODIFIES: this
    // EFFECTS: executes user command
    private void executeCommand(String command) {
        switch (command) {
            case "a":
                doAddVinyl();
                break;
            case "b":
                doRemoveVinyl();
                break;
            case "c":
                doEditVinyl();
                break;
            case "d":
                doShowVinyl();
                break;
            case "e":
                saveVinylList();
                break;
            case "f":
                loadVinylList();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: add a vinyl into the vinyl list
    private void doAddVinyl() {
        System.out.println("Please enter the number of vinyl(s) you want to add: ");
        if (input.hasNextInt()) {
            int numberOfVinyls = input.nextInt();
            if (numberOfVinyls >= 1) {
                for (int i = 0; i <= numberOfVinyls - 1; i++) {
                    System.out.println("Please enter the name of vinyl number " + (i + 1) + ": ");
                    String title = input.next();
                    System.out.println("Please enter the quantity of vinyl number " + (i + 1) + ": ");
                    int quantity = input.nextInt();
                    Vinyl newVinyl = new Vinyl(title, quantity);
                    vinylList.addVinyl(newVinyl);
                }
            }
        } else {
            System.out.println("Invalid input, returning to previous menu...");
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a vinyl from the vinyl list
    private void doRemoveVinyl() {
        doShowVinyl();
        System.out.println("Please choose the vinyl you want to remove, using its ID: ");
        int idOfRemoving = input.nextInt();
        Vinyl vinylRemoving = vinylList.getVinyl(idOfRemoving);
        vinylList.removeVinyl(vinylRemoving);
    }

    // MODIFIES: this
    // EFFECTS: edit a vinyl in the vinyl list
    private void doEditVinyl() {
        doShowVinyl();
        System.out.println("Please choose the vinyl you want to edit, using its ID: ");
        int idOfEditing = input.nextInt();
        Vinyl vinylEditing = vinylList.getVinyl(idOfEditing);
        System.out.println("Please enter the new name of vinyl number:");
        String newTitle = input.next();
        System.out.println("Please enter the new quantity of vinyl number: ");
        int newQuantity = input.nextInt();
        vinylEditing.editVinyl(newTitle, newQuantity);
        System.out.println("The new information of this vinyl is: ");
        System.out.format("%-5s", vinylEditing.getId());
        System.out.format("%-20s", vinylEditing.getTitle());
        System.out.format("%-10s", vinylEditing.getQuantity());
        System.out.print("\n");

    }

    // MODIFIES: this
    // EFFECTS: show all vinyls in the vinyl list
    private void doShowVinyl() {
        if (vinylList.switchToList().size() != 0) {
            int numOfVinyls = 0;
            System.out.format("%-5s", "ID");
            System.out.format("%-20s", "Title");
            System.out.format("%-10s", "Quantity");
            System.out.print("\n");
            if (vinylList.switchToList().size() == 0) {
                System.out.println("There is no vinyl yet.");
            } else {
                while (numOfVinyls <= vinylList.switchToList().size()) {
                    if ((vinylList.getVinyl(numOfVinyls + 1) != null)) {
                        System.out.format("%-5s", vinylList.getVinyl(numOfVinyls + 1).getId());
                        System.out.format("%-20s", vinylList.getVinyl(numOfVinyls + 1).getTitle());
                        System.out.format("%-10s", vinylList.getVinyl(numOfVinyls + 1).getQuantity());
                        System.out.print("\n");
                        numOfVinyls++;
                    } else if (vinylList.getVinyl(numOfVinyls + 1) == null) {
                        numOfVinyls++;
                    }
                }
            }
        }
    }

    // EFFECTS: saves the vinylList to file
    private void saveVinylList() {
        try {
            jsonWriter.open();
            jsonWriter.write(vinylList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads vinylList from file
    private void loadVinylList() {
        try {
            vinylList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from " + JSON_STORE);
        }
    }
}
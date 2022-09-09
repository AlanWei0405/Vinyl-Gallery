package ui;

import model.Event;
import model.EventLog;
import model.VinylList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ControlCenterFrame extends VinylGalleryFrame {
    private static final String JSON_STORE = "./data/vinyl gallery.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ControlCenterFrame(VinylList vinylList) {
        super("Vinyl Gallery Control Center", vinylList);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(500, 200));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JLabel label = new JLabel("Welcome to Vinyl Gallery! ");
        JButton addButton = new JButton("Add vinyl(s)");
        JButton removeButton = new JButton("Remove a vinyl");
        JButton editButton = new JButton("Edit a vinyl");
        JButton displayButton = new JButton("Show current vinyl gallery");
        JButton saveButton = new JButton("Save vinyl gallery to file");
        JButton loadButton = new JButton("Load vinyl gallery from file");
        add(label);
        createButtons(addButton, removeButton, editButton, displayButton, saveButton, loadButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        addLogPrinterOnExit();
    }

    private void addLogPrinterOnExit() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString() + "\n");
                }
                System.exit(0);
            }
        });
    }

    private void createButtons(JButton addButton, JButton removeButton, JButton editButton,
                               JButton displayButton, JButton saveButton, JButton loadButton) {
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        editButton.setActionCommand("edit");
        editButton.addActionListener(this);
        displayButton.setActionCommand("display");
        displayButton.addActionListener(this);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        add(addButton);
        add(removeButton);
        add(editButton);
        add(displayButton);
        add(saveButton);
        add(loadButton);
    }

    //This is the method that is called when one JButton is clicked
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                doAddVinyl();
                break;
            case "remove":
                doRemoveVinyl();
                break;
            case "edit":
                doEditVinyl();
                break;
            case "display":
                doShowVinyl();
                break;
            case "save":
                saveVinylList();
                break;
            case "load":
                loadVinylList();
                break;
        }
    }

    private void doAddVinyl() {
        try {
            String input = JOptionPane.showInputDialog(this,
                    "Please enter the number of vinyl(s) you want to add: ",
                    "Number of new vinyl(s)", JOptionPane.INFORMATION_MESSAGE);
            if (input != null) {
                int numberOfVinyls = Integer.parseInt(input);
                if (numberOfVinyls >= 1) {
                    for (int i = 0; i <= numberOfVinyls - 1; i++) {
                        new AddVinylFrame(vinylList);
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input",
                    "Success Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doRemoveVinyl() {
        new RemoveVinylFrame(vinylList);

    }

    private void doEditVinyl() {
        new EditVinylFrame(vinylList);
    }

    private void doShowVinyl() {
        if (vinylList.switchToList().size() != 0) {
            new ShowVinylGalleryFrame(vinylList);
        }
    }

    private void saveVinylList() {
        int userOption = JOptionPane.showConfirmDialog(this, "Confirm to save to file?",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userOption == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(vinylList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(this, "Saved to " + JSON_STORE,
                        "Success Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Unable to save to " + JSON_STORE,
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadVinylList() {
        int userOption = JOptionPane.showConfirmDialog(this, "Confirm to load from file?",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userOption == JOptionPane.YES_OPTION) {
            try {
                vinylList = jsonReader.read();
                JOptionPane.showMessageDialog(this, "Loaded from " + JSON_STORE,
                        "Success Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Unable to read from " + JSON_STORE,
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
package ui;

import model.Vinyl;
import model.VinylList;

import javax.swing.*;
import java.awt.*;

public class AddVinylFrame extends VinylGalleryFrame {

    public AddVinylFrame(VinylList vinylList) {
        super("Add vinyl(s)", vinylList);
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(0, 2, 2, 2));
        JTextField titleField = new JTextField(20);
        JTextField quantityField = new JTextField(5);
        addPanel.add(new JLabel("Enter the title of the vinyl: "));
        addPanel.add(titleField);
        addPanel.add(new JLabel("Enter the quantity of the vinyl: "));
        addPanel.add(quantityField);
        int option = JOptionPane.showConfirmDialog(this, addPanel,
                "Add vinyl(s)", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            try {
                String title = titleField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                vinylList.addVinyl(new Vinyl(title, quantity));
                addConfirm(title, quantity);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addConfirm(String title, int quantity) {
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.PAGE_AXIS));
        confirmPanel.add(new JLabel("Title: " + title));
        confirmPanel.add(new JLabel("Quantity: " + quantity));
        JOptionPane.showMessageDialog(this, confirmPanel,
                "Success Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
package ui;

import model.Vinyl;
import model.VinylList;

import javax.swing.*;
import java.awt.*;

public class EditVinylFrame extends VinylGalleryFrame {

    public EditVinylFrame(VinylList vinylList) {
        super("Edit a vinyl", vinylList);
        JPanel editPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        infoPanel.setLayout(new GridLayout(0, 2));
        JTextField idField = new JTextField(5);
        JTextField titleField = new JTextField(20);
        JTextField quantityField = new JTextField(5);
        JTable table = new JTable(vinylList);
        table.getTableHeader().setOpaque(false);    //Make sure the header is visible in both Light Mode and Dark Mode
        infoPanel.add(new JLabel("Please enter the number of vinyl you want to edit: "));
        infoPanel.add(idField);
        infoPanel.add(new JLabel("Enter the new title: "));
        infoPanel.add(titleField);
        infoPanel.add(new JLabel("Enter the new quantity: "));
        infoPanel.add(quantityField);
        editPanel.add(infoPanel);
        editPanel.add(new JScrollPane(table));
        int option = JOptionPane.showConfirmDialog(this, editPanel,
                "Edit a vinyl", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            tryEdit(idField, titleField, quantityField, vinylList);
        }

    }

    private void tryEdit(JTextField idField, JTextField titleField, JTextField quantityField, VinylList vinylList) {
        try {
            int idOfEditing = Integer.parseInt(idField.getText());
            try {
                Vinyl vinylBeingEdited = vinylList.getVinyl(idOfEditing);
                editConfirm(titleField.getText(), Integer.parseInt(quantityField.getText()),
                        vinylBeingEdited, idOfEditing);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null,
                        "The vinyl you are trying to edit does not exist.",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input",
                    "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editConfirm(String newTitle, int newQuantity, Vinyl vinyl, int idOfEditing) {
        int userOption = JOptionPane.showConfirmDialog(this,
                "Confirm to edit the vinyl to the following? \n"
                        + "\"" + vinyl.getId() + "\t" + newTitle + "\t" + newQuantity + "\"",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userOption == JOptionPane.YES_OPTION) {
            vinylList.getVinyl(idOfEditing).setTitle(newTitle);
            vinylList.getVinyl(idOfEditing).setQuantity(newQuantity);
        }
    }
}

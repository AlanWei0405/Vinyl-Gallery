package ui;

import model.Vinyl;
import model.VinylList;

import javax.swing.*;

public class RemoveVinylFrame extends VinylGalleryFrame {

    public RemoveVinylFrame(VinylList vinylList) {
        super("Remove vinyl(s)", vinylList);
        JPanel removePanel = new JPanel();
        JTextField idField = new JTextField(5);
        JTable table = new JTable(vinylList);
        table.getTableHeader().setOpaque(false);    //Make sure the header is visible in both Light Mode and Dark Mode
        removePanel.add(new JLabel("Please enter the number of vinyl you want to remove: "));
        removePanel.add(idField);
        removePanel.add(new JScrollPane(table));
        int option = JOptionPane.showConfirmDialog(this, removePanel,
                "Remove vinyl(s)", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            tryRemove(idField, vinylList);
        }
    }

    private void tryRemove(JTextField idField, VinylList vinylList) {
        try {
            int idOfRemoving = Integer.parseInt(idField.getText());
            try {
                Vinyl vinylBeingRemoved = vinylList.getVinyl(idOfRemoving);
                removeConfirm(vinylBeingRemoved, idOfRemoving);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null,
                        "The vinyl you are trying to remove does not exist.",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input",
                    "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeConfirm(Vinyl vinyl, int idOfRemoving) {
        int userOption = JOptionPane.showConfirmDialog(this,
                "Confirm to remove the following vinyl? \n"
                        + "\"" + vinyl.getId() + "\t" + vinyl.getTitle() + "\t" + vinyl.getQuantity() + "\"",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userOption == JOptionPane.YES_OPTION) {
            vinylList.removeVinyl(vinylList.getVinyl(idOfRemoving));
        }
    }
}

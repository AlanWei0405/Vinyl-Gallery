package ui;

import model.VinylList;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowVinylGalleryFrame extends VinylGalleryFrame {

    public ShowVinylGalleryFrame(VinylList vinylList) {
        super("Show current vinyl gallery", vinylList);
        JTable table = new JTable(vinylList);
        table.getTableHeader().setOpaque(false);    //Make sure the header is visible in both Light Mode and Dark Mode
        add(new JScrollPane(table));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

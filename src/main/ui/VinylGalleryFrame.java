package ui;

import model.VinylList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class VinylGalleryFrame extends JFrame implements ActionListener {
    protected VinylList vinylList;

    public VinylGalleryFrame(String frameTitle, VinylList vinylList) {
        super(frameTitle);
        this.vinylList = vinylList;
    }

    public void actionPerformed(ActionEvent e) {

    }
}

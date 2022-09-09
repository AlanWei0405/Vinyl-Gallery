package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SplashScreen extends JWindow {
    private static final String ICON_STORE = "Images/vinyl.png";

    public SplashScreen() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException
                        | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                showSplash();
            }
        });
    }

    public void showSplash() {

        JPanel content = (JPanel) getContentPane();

        // Set the window's bounds, centering the window
        int width = 500;
        int height = 500;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        buildSplashScreen(content);

        // Display it
        setVisible(true);
        toFront();

        new ResourceLoader().execute();
    }

    private void buildSplashScreen(JPanel content) {
        // Build the splash screen
        JLabel label = new JLabel(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(ICON_STORE))));

        content.add(label, BorderLayout.CENTER);

        label.setLayout(new GridBagLayout());
    }

    public class ResourceLoader extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {
            // Wait a little while, maybe while loading resources
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Cannot start application",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }

        @Override
        protected void done() {
            setVisible(false);
        }
    }
}

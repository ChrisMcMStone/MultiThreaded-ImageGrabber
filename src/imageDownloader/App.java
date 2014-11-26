package imageDownloader;

import javax.swing.*;

/**
 * Runs the program by running the MainGUI JFrame in a separate thread
 *
 * Created by chris on 20/11/14.
 */
public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });

    }
}

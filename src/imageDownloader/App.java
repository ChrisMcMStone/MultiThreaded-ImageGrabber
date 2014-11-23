package imageDownloader;

import javax.swing.*;

/**
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

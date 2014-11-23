package imageDownloader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Chris Stone
 */
public class MainGUI extends JFrame {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Chris Stone
    private JMenuBar menuBar1;
    private JMenu mainMenu;
    private JMenuItem settings;
    private JMenuItem exit;
    private JScrollPane scrollPane1;
    private JTable imageTable;
    private DefaultTableModel model;
    private JTextField urlField;
    private JComboBox imageFormats;
    private JButton pasteURL;
    private JButton getImages;
    private JButton save;
    private JSlider maxImages;
    private JLabel lblType;
    private JLabel lblMax;
    private JLabel lblUrl;
    private JButton clear;
    private String userDirec = System.getProperty("user.dir");
    private int threadNo = 2;
    private int maxImgNo = 50;


    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public MainGUI() {
        super("Image Downloader");
        initComponents();
        initButtons();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initButtons() {

        getImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GetImage(urlField.getText(), imageFormats.getSelectedItem().toString(),
                        model, threadNo, maxImgNo).execute();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            for (int i = 0; i < model.getRowCount(); i++) {
                                String fileName = (String) model.getValueAt(i, 1);
                                String fileEx = fileName.substring(fileName.indexOf('.') + 1, fileName.length());
                                ImageIO.write(((RenderedImage) model.getValueAt(i, 0)), fileEx, new File(userDirec + fileName));
                            }
                        } catch (IOException r) {
                            r.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "Images saved to file system.");
                        return null;
                    }
                }.execute();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
            }
        });

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsGUI gui = new SettingsGUI(threadNo, userDirec) {
                    @Override
                    protected void changeSettings(int thNo, String direc) {
                        threadNo = thNo;
                        userDirec = direc;
                    }
                };
                gui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                gui.setVisible(true);
            }
        });

        pasteURL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    urlField.setText(Toolkit.getDefaultToolkit()
                            .getSystemClipboard().getData(DataFlavor.stringFlavor).toString());
                } catch (IOException | UnsupportedFlavorException r) {
                    r.printStackTrace();
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Chris Stone
        menuBar1 = new JMenuBar();
        mainMenu = new JMenu();
        settings = new JMenuItem();
        exit = new JMenuItem();
        scrollPane1 = new JScrollPane();
        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Image preview", "Name", "Size", "Resolution"});
        imageTable = new JTable(model);
        TableColumnModel tcM = imageTable.getColumnModel();
        tcM.getColumn(0).setCellRenderer(new ImageCellRenderer());
        imageTable.setRowHeight(50);
        urlField = new JTextField();
        imageFormats = new JComboBox();
        pasteURL = new JButton();
        getImages = new JButton();
        save = new JButton();
        maxImages = new JSlider();
        maxImages.setMinimum(1);
        maxImages.setMaximum(100);
        maxImages.setMajorTickSpacing(5);
        maxImages.setValue(maxImgNo);
        lblType = new JLabel();
        lblMax = new JLabel();
        lblUrl = new JLabel();
        clear = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== mainMenu ========
            {
                mainMenu.setText("File");

                //---- settings ----
                settings.setText("Settings");
                mainMenu.add(settings);

                //---- exit ----
                exit.setText("Exit");
                mainMenu.add(exit);
            }
            menuBar1.add(mainMenu);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(imageTable);
        }

        //======== imageFormats ========
        imageFormats.addItem("All");
        imageFormats.addItem("jpeg");
        imageFormats.addItem("png");
        imageFormats.addItem("gif");

        //---- pasteURL ----
        pasteURL.setText("Paste");

        //---- getImages ----
        getImages.setText("Get Images");

        //---- save ----
        save.setToolTipText("Save selected images to specified directory");
        save.setIcon(new ImageIcon("/home/chris/Documents/uni_yr2/SSC/Multi-threading/src/save.png"));

        //---- lblType ----
        lblType.setText("Image type :");

        //---- lblMax ----
        lblMax.setText("Max no. of images:");

        //---- lblUrl ----
        lblUrl.setText("URL: ");

        //---- clear ----
        clear.setToolTipText("Save selected images to specified directory");
        clear.setIcon(new ImageIcon("/home/chris/Documents/uni_yr2/SSC/Multi-threading/src/clear.png"));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(save, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(clear, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(568, Short.MAX_VALUE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(lblUrl)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                                                .addComponent(urlField, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addComponent(lblType)
                                                                        .addComponent(lblMax))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(maxImages, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                                                        .addComponent(imageFormats, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                                .addComponent(pasteURL)
                                                .addGap(30, 30, 30)
                                                .addComponent(getImages)
                                                .addGap(26, 26, 26))))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(save)
                                        .addComponent(clear))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUrl)
                                        .addComponent(urlField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pasteURL)
                                        .addComponent(getImages))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblType)
                                        .addComponent(imageFormats, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblMax)
                                        .addComponent(maxImages, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    private class ImageCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            Image newImg = ((Image) value).getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(newImg));
            return label;
        }
    }
}

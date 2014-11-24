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
    private JTextField urlField;
    private JComboBox imageFormats;
    private JButton pasteURL;
    private JButton getImages;
    private JButton save;
    private JSlider maxImgSlider;
    private JLabel lblType;
    private JLabel lblMax;
    private JLabel lblUrl;
    private JButton clear;
    private JLabel label4;
    private JLabel label5;
    private JProgressBar progressBar1;
    private JLabel lblImageProg;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private String userDirec = System.getProperty("user.dir");
    private int threadNo = 2;
    private int maxImgNo = 50;
    private DefaultTableModel model;

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
                progressBar1.setVisible(true);
                new GetImage(urlField.getText(),
                        imageFormats.getSelectedItem().toString(),
                        model, threadNo, maxImgSlider.getValue(), progressBar1, lblImageProg).execute();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            int[] selectedImages = imageTable.getSelectedRows();
                            for (int i = 0; i < selectedImages.length; i++) {
                                String fileName = (String) model.getValueAt(i, 1);
                                String fileEx = fileName.substring(fileName.indexOf('.') + 1, fileName.length());
                                ImageIO.write(((RenderedImage) model.getValueAt(i, 0)), fileEx, new File(userDirec + "/" + fileName));
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
                progressBar1.setValue(0);
                progressBar1.setVisible(false);
                lblImageProg.setText("");
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
        imageTable = new JTable();
        urlField = new JTextField();
        imageFormats = new JComboBox();
        pasteURL = new JButton();
        getImages = new JButton();
        save = new JButton();
        maxImgSlider = new JSlider();
        lblType = new JLabel();
        lblMax = new JLabel();
        lblUrl = new JLabel();
        clear = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        progressBar1 = new JProgressBar();
        lblImageProg = new JLabel();

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

        //---- label4 ----
        label4.setText("1");

        //---- label5 ----
        label5.setText("100");

        //---- lblImageProg ----
        lblImageProg.setVisible(false);

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
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(progressBar1, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                                                                .addContainerGap())
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addGap(196, 196, 196)
                                                                .addComponent(lblImageProg)
                                                                .addContainerGap(339, Short.MAX_VALUE))))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(lblUrl)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                                                .addComponent(urlField, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addComponent(lblType)
                                                                        .addComponent(lblMax))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addGap(0, 4, Short.MAX_VALUE)
                                                                                .addGroup(contentPaneLayout.createParallelGroup()
                                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                                .addGap(6, 6, 6)
                                                                                                .addComponent(label4)
                                                                                                .addGap(169, 169, 169)
                                                                                                .addComponent(label5))
                                                                                        .addComponent(imageFormats, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                                .addComponent(maxImgSlider, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 4, Short.MAX_VALUE)))))
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
                                        .addComponent(clear)
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                                .addComponent(lblImageProg)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(progressBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
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
                                        .addComponent(maxImgSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label5)
                                        .addComponent(label4))
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        model = new DefaultTableModel(new Object[][]{}, new Object[]{"Image preview", "Name", "Size", "Resolution"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return columnIndex == 4;
            }
        };
        imageTable.setModel(model);
        imageTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        progressBar1.setVisible(false);
        TableColumnModel tcM = imageTable.getColumnModel();
        tcM.getColumn(0).setCellRenderer(new ImageCellRenderer());
        imageTable.setRowHeight(50);
        imageFormats.addItem("All");
        imageFormats.addItem("jpeg");
        imageFormats.addItem("png");
        imageFormats.addItem("gif");
        maxImgSlider.setMinimum(1);
        maxImgSlider.setMaximum(100);
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

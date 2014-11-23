/*
 * Created by JFormDesigner on Sat Nov 22 19:11:20 GMT 2014
 */

package imageDownloader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Chris Stone
 */
public class SettingsGUI extends JFrame {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Chris Stone
    private JLabel label1;
    private JLabel lblThread;
    private JButton btnSave;
    private JTextField fieldPath;
    private JButton btnFileChoose;
    private JSpinner threadNoSpinner;
    private JButton btnClose;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private int threadNo;
    private String userDirec;

    public SettingsGUI(int threadNumber, String direc) {
        super("Settings");
        threadNo = threadNumber;
        userDirec = direc;
        initComponents();
        initButtons();
    }

    private void initButtons() {

        btnFileChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fieldPath.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeSettings((int) threadNoSpinner.getValue(), fieldPath.getText());
                JOptionPane.showMessageDialog(null, "Preferences have been applied.");
                dispose();
            }
        });

    }

    protected void changeSettings(int thNo, String direc) {
        //This is overridden
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Chris Stone
        label1 = new JLabel();
        lblThread = new JLabel();
        btnSave = new JButton();
        fieldPath = new JTextField(userDirec);
        btnFileChoose = new JButton();
        threadNoSpinner = new JSpinner();
        threadNoSpinner.setValue(threadNo);
        btnClose = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Path to save images: ");

        //---- lblThread ----
        lblThread.setText("Max. no of threads: ");

        //---- btnSave ----
        btnSave.setText("Save");

        //---- btnFileChoose ----
        btnFileChoose.setText("Choose...");

        //---- btnClose ----
        btnClose.setText("Close");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                                                        .addComponent(label1)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnFileChoose))
                                                .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(lblThread)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(threadNoSpinner, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                                .addComponent(btnClose)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(btnSave))))
                                        .addComponent(fieldPath, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(24, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label1)
                                        .addComponent(btnFileChoose))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addComponent(fieldPath, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(lblThread))
                                        .addComponent(threadNoSpinner, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btnSave)
                                        .addComponent(btnClose))
                                .addContainerGap(24, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


}

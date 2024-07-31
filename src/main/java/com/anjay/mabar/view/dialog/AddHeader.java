package com.anjay.mabar.view.dialog;

import com.anjay.mabar.models.EmailHeaderTable;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class AddHeader extends JDialog {
    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JTable header;
    private JButton importButton;
    private JButton add;
    private JButton testButton;

    public AddHeader() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);

        EmailHeaderTable tableModel = new EmailHeaderTable();
        header.setModel(tableModel);

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, "test", "testtt"});
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onImport();
            }
        });
    }

    private void onImport() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                EmailHeaderTable tableModel = (EmailHeaderTable) header.getModel();
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, parts[0], parts[1]});
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void onSave() {
        EmailHeaderTable tableModel = (EmailHeaderTable) header.getModel();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Fajar\\Documents\\Sender\\headers.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.write(tableModel.getValueAt(i, 1).toString() + "|" + tableModel.getValueAt(i, 2).toString());
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dispose();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddHeader dialog = new AddHeader();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailHeaderTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CustomHeaderController implements ActionListener {
    private EmailHeaderTable emailHeaderTable;
    private JTable header;

    public CustomHeaderController(EmailHeaderTable emailHeaderTable, JTable header) {
        this.emailHeaderTable = emailHeaderTable;
        this.header = header;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "[ + ] Add":
                emailHeaderTable.addRow(new Object[]{emailHeaderTable.getRowCount() + 1, "", ""});
                break;
            case "Import":
                onImport();
                break;
            case "Save":
                onSave();
                break;
            case "Clear":
                emailHeaderTable.setRowCount(0);
                break;
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
}

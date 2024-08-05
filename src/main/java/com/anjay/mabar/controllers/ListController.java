package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailListTable;
import com.anjay.mabar.models.EmailList;
import com.anjay.mabar.models.ListModel;
import com.anjay.mabar.utils.TxtReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListController implements ActionListener {

    private EmailListTable model;
    private List<String> emailAddresses;
    private ListModel listModel;

    public ListController(EmailListTable model, ListModel listModel) {
        this.model = model;
        this.listModel = listModel;
        this.emailAddresses = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Import":
                onImport();
                break;
            case "Clear All":
                onClear();
                break;
            case "Clear Success":
                onClearSuccessList();
                break;
            case "[ +Add ]":
                onAdd();
                break;

        }
    }

    private void onAdd() {
        model.addRow(new Object[]{model.getRowCount() + 1, "", ""});
    }

    private void onClearSuccessList() {
        List<Integer> indicesToRemove = new ArrayList<>();

        // Collect indices of rows to be removed
        for (int i = 0; i < model.getRowCount(); i++) {
            if ("Sent!".equals(model.getValueAt(i, 2))) {
                indicesToRemove.add(i);
            }
        }

        // Remove rows from model and emailAddresses in reverse order
        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            int index = indicesToRemove.get(i);
            model.removeRow(index);
            if (index < emailAddresses.size()) {
                emailAddresses.remove(index);
            }
        }

        for (int i = 0; i < emailAddresses.size(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
    }

    private void onClear() {
        model.setRowCount(0);
        emailAddresses.clear();
    }

    private void onImport() {
        //        JFileChooser fileChooser = new JFileChooser();
//        int result = fileChooser.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            System.out.println(selectedFile.getAbsolutePath());
//            fetchEmailList(selectedFile.getAbsolutePath());
//        }

        fetchEmailList("/home/fajar/Documents/list.txt");
    }

    private void fetchEmailList(String path) {
        List<EmailList> emailLists = TxtReader.readTxt(path);

        int count = model.getRowCount();
        for (EmailList emailList : emailLists) {
            model.addRow(new Object[]{count++ + 1, emailList.getEmailAddress(), ""});
            emailAddresses.add(emailList.getEmailAddress());
        }
    }
}
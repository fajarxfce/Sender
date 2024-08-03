package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailListTable;
import com.anjay.mabar.models.EmailList;
import com.anjay.mabar.utils.TxtReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportListController implements ActionListener {

    private EmailListTable model;
    private List<String> emailAddresses;

    public ImportListController(EmailListTable model) {
        this.model = model;
        this.emailAddresses = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            fetchEmailList(selectedFile.getAbsolutePath());
        }

//        fetchEmailList("C:\\Users\\Fajar\\Documents\\webde.txt");
    }

    private void fetchEmailList(String path) {
        List<EmailList> emailLists = TxtReader.readTxt(path);

        int count = model.getRowCount();
        for (EmailList emailList : emailLists) {
            model.addRow(new Object[]{count++ + 1, emailList.getEmailAddress(), ""});
            emailAddresses.add(emailList.getEmailAddress());
        }
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }
}
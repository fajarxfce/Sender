package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailList;
import com.anjay.mabar.models.EmailListPath;
import com.anjay.mabar.models.EmailListTable;
import com.anjay.mabar.utils.TxtReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class ImportListController implements ActionListener {
    private EmailListTable model;
    private EmailListPath path;

    public ImportListController(EmailListTable model, EmailListPath path) {
        this.model = model;
        this.path = path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
//            smtpPath.setPath(selectedFile.getAbsolutePath());
            System.out.println(selectedFile.getAbsolutePath());
            fetchEmailList(selectedFile.getAbsolutePath());
        }

    }

    private void fetchEmailList(String emailListpath) {
        // Fetch EmailList from path
        List<EmailList> emailLists = TxtReader.readTxt(emailListpath);

        int count = model.getRowCount();
        for (EmailList emailList : emailLists) {
            model.addRow(new Object[]{count++, emailList.getEmailAddress()});
        }
        path.setCount(emailLists.size());
    }
}

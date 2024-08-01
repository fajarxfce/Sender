package com.anjay.mabar.controllers;

import com.anjay.mabar.models.SMTPPath;
import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.models.SMTPTableModel;
import com.anjay.mabar.models.Smtp;
import com.anjay.mabar.utils.CsvReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class SelectSMTPController implements ActionListener {

    private SMTPPath smtpPath;
    private SMTPTableModel model;

    public SelectSMTPController(SMTPPath smtpPath, SMTPTableModel model) {
        this.smtpPath = smtpPath;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        JFileChooser fileChooser = new JFileChooser();
//        int result = fileChooser.showOpenDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            System.out.println(selectedFile.getAbsolutePath());
////            fetchSmtp(selectedFile.getAbsolutePath());
//            fetchSmtp("C:\\Users\\Fajar\\Documents\\smtp.csv");
//        }

        fetchSmtp("C:\\Users\\Fajar\\Documents\\smtp.csv");
    }

    private void fetchSmtp(String path) {
        List<Smtp> smtps = CsvReader.readCsv(path);

        int count = model.getRowCount();
        for (Smtp smtp : smtps) {
            model.addRow(new Object[]{count++ + 1, smtp.getEmailAddress(), smtp.getPassword()});
        }
        smtpPath.setCount(smtps.size());
    }
}
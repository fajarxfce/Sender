package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailDetails;
import com.anjay.mabar.models.EmailListTable;
import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.models.SMTPTableModel;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.worker.SendEmailWorker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SendMainController implements ActionListener, SendMailObserver {
    private EmailListTable model;
    private SMTPTableModel smtpTableModel;
    private ImportListController importListController;
    private int connectionCount;
    private JTextArea textAreaSubject;
    private JTextArea textAreaFromName;
    private JTextArea textAreaBody;

    public SendMainController(EmailListTable model, SMTPTableModel smtpTableModel, ImportListController importListController, int connectionCount, JTextArea textAreaSubject, JTextArea textAreaFromName, JTextArea textAreaBody) {
        this.model = model;
        this.smtpTableModel = smtpTableModel;
        this.importListController = importListController;
        this.connectionCount = connectionCount;
        this.textAreaSubject = textAreaSubject;
        this.textAreaFromName = textAreaFromName;
        this.textAreaBody = textAreaBody;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<SMTPServer> smtpServers = getSMTPServers(smtpTableModel);
        List<String> emailList = importListController.getEmailAddresses();
        EmailDetails emailDetails = new EmailDetails(textAreaSubject.getText(), textAreaFromName.getText(), textAreaBody.getText());
        SendEmailWorker worker = new SendEmailWorker(emailList, smtpServers, connectionCount, emailDetails);
        worker.addObserver(this);
        String command = e.getActionCommand();
        if ("START".equals(command)) {
            System.out.println("Start sending email");
            worker.execute();
        } else if ("STOP".equals(command)) {
            System.out.println("Stop sending email");
            worker.pause();
        }

    }

    private void updateEmailStatus(String email, String status) {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (model.getValueAt(i, 1).equals(email)) {
                System.out.println("Updating email status: " + email + " to " + status);
                model.setValueAt(status, i, 2); // Assuming the status column is at index 1
                break;
            }
        }
    }

    @Override
    public void onSendMailSuccess(String email) {
        System.out.println("Email sent to: " + email);
        updateEmailStatus(email, "Failed");
    }

    @Override
    public void onSendMailFailed(String message) {
        System.out.println("Failed to send email: " + message);
        updateEmailStatus(message, "Failed");
    }

    private List<SMTPServer> getSMTPServers(SMTPTableModel smtpTableModel) {
        List<SMTPServer> smtpServers = new ArrayList<>();
        int rowCount = smtpTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String username = (String) smtpTableModel.getValueAt(i, 1);
            String password = (String) smtpTableModel.getValueAt(i, 3);
            smtpServers.add(new SMTPServer(username, password));
        }
        return smtpServers;
    }
}

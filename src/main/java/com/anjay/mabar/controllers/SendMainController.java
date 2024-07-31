package com.anjay.mabar.controllers;

import com.anjay.mabar.models.*;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.worker.SendEmailWorker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SendMainController implements ActionListener, SendMailObserver {

    private SendEmailConfig emailConfig;

    public SendMainController(SendEmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<SMTPServer> smtpServers = getSMTPServers(emailConfig.getSmtpTableModel());
        List<String> emailList = emailConfig.getImportListController().getEmailAddresses();
        String subject = emailConfig.getTextAreaSubject().getText();
        String fromName = emailConfig.getTextAreaFromName().getText();
        String body = emailConfig.getTextAreaBody().getText();
        int connectionCount = (int) emailConfig.getConnectionCount();
        EmailDetails emailDetails = new EmailDetails.Builder()
                .setSubject(subject)
                .setFromName(fromName)
                .setBody(body)
                .build();
        SendEmailWorker worker = new SendEmailWorker(emailList, smtpServers, connectionCount, emailDetails);
        worker.addObserver(this);
        worker.execute();

    }

    private void updateEmailStatus(String email, String status) {
        int rowCount = emailConfig.getEmailListTable().getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (emailConfig.getEmailListTable().getValueAt(i, 1).equals(email)) {
                System.out.println("Updating email status: " + email + " to " + status);
                emailConfig.getEmailListTable().setValueAt(status, i, 2); // Assuming the status column is at index 1
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

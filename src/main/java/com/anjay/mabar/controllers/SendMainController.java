package com.anjay.mabar.controllers;

import com.anjay.mabar.factory.SMTPServerFactory;
import com.anjay.mabar.models.*;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.worker.SendEmailWorker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SendMainController implements ActionListener, SendMailObserver {

    private SendEmailConfig emailConfig;

    public SendMainController(SendEmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SMTPTableModel smtpTableModel = emailConfig.getSmtpTableModel();
        String subjectText = emailConfig.getTextAreaSubject().getText();
        String fromNameText = emailConfig.getTextAreaFromName().getText();
        List<String> subjectList = Arrays.asList(subjectText.split("\\r?\\n"));
        List<String> fromNameList = Arrays.asList(fromNameText.split("\\r?\\n"));
        String body = emailConfig.getTextAreaBody().getText();
        String contentType = emailConfig.getContentType();

        System.out.println(body);
        List<String> emailList = emailConfig.getImportListController().getEmailAddresses();
        int connectionCount = (int) emailConfig.getConnectionCount();
        List<SMTPServer> smtpServers = SMTPServerFactory.createSMTPServers(smtpTableModel);
        EmailDetails emailDetails = new EmailDetails.Builder()
                .setSubject(subjectList)
                .setFromName(fromNameList)
                .setBody(body)
                .setContentType(contentType)
                .build();
        SendEmailWorker worker = new SendEmailWorker(emailList, smtpServers, connectionCount, emailDetails);
        worker.addObserver(this);
        worker.execute();

    }

    private void updateEmailStatus(String email, String status) {
        int rowCount = emailConfig.getEmailListTable().getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (emailConfig.getEmailListTable().getValueAt(i, 1).equals(email)) {
//                System.out.println("Updating email status: " + email + " to " + status);
                emailConfig.getEmailListTable().setValueAt(status, i, 2); // Assuming the status column is at index 1
                break;
            }
        }
    }

    @Override
    public void onSendMailSuccess(String email) {
//        System.out.println("Email sent to: " + email);
        updateEmailStatus(email, "Failed");
    }

    @Override
    public void onSendMailFailed(String message) {
//        System.out.println("Failed to send email: " + message);
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

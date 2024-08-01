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

    private void updateEmailStatus(String status, int index, String sendBy) {
        emailConfig.getEmailListTable().setValueAt(status, index, 2);
        emailConfig.getEmailListTable().setValueAt(sendBy, index, 3);
    }

    @Override
    public void onSendMailSuccess(String status, int index, String sendBy) {
        updateEmailStatus(status, index, sendBy);
    }

    @Override
    public void onSendMailFailed(String status, int index, String sendBy) {
        updateEmailStatus(status, index, sendBy);
    }
}

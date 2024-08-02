package com.anjay.mabar.controllers;

import com.anjay.mabar.factory.EmailHeaderFactory;
import com.anjay.mabar.factory.EmailListFactory;
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
    private SendingConfig config;
    public SendMainController(SendEmailConfig emailConfig, SendingConfig config) {
        this.emailConfig = emailConfig;
        this.config = config;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SMTPTableModel smtpTableModel = emailConfig.getSmtpTableModel();
        EmailListTable emailListTable = emailConfig.getEmailListTable();
        EmailHeaderTable headerTable = emailConfig.getHeaderTable();
        String subjectText = emailConfig.getTextAreaSubject().getText();
        String fromNameText = emailConfig.getTextAreaFromName().getText();
        List<String> subjectList = Arrays.asList(subjectText.split("\\r?\\n"));
        List<String> fromNameList = Arrays.asList(fromNameText.split("\\r?\\n"));
        String body = emailConfig.getTextAreaBody().getText();
        String contentType = emailConfig.getContentType();
        String messageID = emailConfig.getMessageID().getText();

        String con = config.getConnectionCount().getValue().toString();
        String sleep = config.getSleepTime().getValue().toString();
        String thread = config.getThreadCount().getValue().toString();
        int priority = config.getMailPriority().getSelectedIndex() + 1;

        SendConfig sendConfig = new SendConfig(con, sleep, thread, priority);

        List<String> emailList = emailConfig.getImportListController().getEmailAddresses();
        int connectionCount = (int) emailConfig.getConnectionCount();
        List<SMTPServer> smtpServers = SMTPServerFactory.createSMTPServers(smtpTableModel);
        List<EmailList> emailLists = EmailListFactory.createEmailList(emailListTable);
        List<EmailHeader> emailHeaders = EmailHeaderFactory.createEmailHeader(headerTable);
        EmailDetails emailDetails = new EmailDetails.Builder()
                .setSubject(subjectList)
                .setFromName(fromNameList)
                .setBody(body)
                .setMessageID(messageID)
                .setContentType(contentType)
                .build();
        SendEmailWorker worker = new SendEmailWorker(emailLists, smtpServers, emailHeaders,  connectionCount, emailDetails, sendConfig);
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

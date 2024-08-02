package com.anjay.mabar.controllers;

import com.anjay.mabar.factory.EmailHeaderFactory;
import com.anjay.mabar.factory.EmailListFactory;
import com.anjay.mabar.factory.SMTPServerFactory;
import com.anjay.mabar.models.*;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.worker.SendEmailWorker;
import org.simplejavamail.api.email.ContentTransferEncoding;

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
        System.out.println(e.getActionCommand());
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
        String replyTo = emailConfig.getTxtReplyTo().getText();
        String bounceTo = emailConfig.getTxtBounceTo().getText();

        String con = config.getConnectionCount().getValue().toString();
        String sleep = config.getSleepTime().getValue().toString();
        String thread = config.getThreadCount().getValue().toString();
        int priority = config.getMailPriority().getSelectedIndex() + 1;

        ComboBoxModel<ContentTransferEncoding> encoding = config.getEncoding().getModel();
        ContentTransferEncoding contentTransferEncoding = (ContentTransferEncoding) encoding.getSelectedItem();

        SendConfig sendConfig = new SendConfig(con, thread, sleep, priority, contentTransferEncoding);



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
                .setReplyTo(replyTo)
                .setBounceTo(bounceTo)
                .build();
        SendEmailWorker worker = new SendEmailWorker(emailLists, smtpServers, emailHeaders,  connectionCount, emailDetails, sendConfig);
        worker.addObserver(this);
        if (e.getActionCommand().equals("START")) {
            worker.execute();
        } else {
            worker.cancel();
        }
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

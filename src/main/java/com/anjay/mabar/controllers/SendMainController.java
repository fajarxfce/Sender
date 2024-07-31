package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailListTable;
import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.models.SMTPTableModel;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.worker.SendEmailWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SendMainController implements ActionListener, SendMailObserver {
    private EmailListTable model;
    private SMTPTableModel smtpTableModel;
    private ImportListController importListController;
    private int connectionCount;

    public SendMainController(EmailListTable model, SMTPTableModel smtpTableModel, ImportListController importListController, int connectionCount) {
        this.model = model;
        this.smtpTableModel = smtpTableModel;
        this.importListController = importListController;
        this.connectionCount = connectionCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        List<SMTPServer> smtpServers = getSMTPServers(smtpTableModel);

        List<String> emailList = importListController.getEmailAddresses();
        SendEmailWorker worker = new SendEmailWorker(emailList, smtpServers, connectionCount);
        worker.addObserver(this);
        worker.execute();
    }

    @Override
    public void onSendMailSuccess(String email) {
        System.out.println("Email sent to: " + email);
    }

    @Override
    public void onSendMailFailed(String message) {
        System.out.println("Failed to send email: " + message);
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

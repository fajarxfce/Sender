package com.anjay.mabar.factory;

import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.models.SMTPTableModel;

import java.util.ArrayList;
import java.util.List;

public class SMTPServerFactory {
    public static List<SMTPServer> createSMTPServers(SMTPTableModel smtpTableModel) {
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
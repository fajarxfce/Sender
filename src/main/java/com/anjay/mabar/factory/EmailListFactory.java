
package com.anjay.mabar.factory;

import com.anjay.mabar.models.EmailList;
import com.anjay.mabar.models.EmailListTable;
import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.models.SMTPTableModel;

import java.util.ArrayList;
import java.util.List;

public class EmailListFactory {
    public static List<EmailList> createEmailList(EmailListTable table) {
        List<EmailList> emailLists = new ArrayList<>();
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String email = (String) table.getValueAt(i, 1);
            emailLists.add(new EmailList(email));
        }
        return emailLists;
    }
}
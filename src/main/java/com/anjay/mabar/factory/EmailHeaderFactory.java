
package com.anjay.mabar.factory;

import com.anjay.mabar.models.EmailHeader;
import com.anjay.mabar.models.EmailHeaderTable;
import com.anjay.mabar.models.EmailList;
import com.anjay.mabar.models.EmailListTable;

import java.util.ArrayList;
import java.util.List;

public class EmailHeaderFactory {
    public static List<EmailHeader> createEmailHeader(EmailHeaderTable table) {
        List<EmailHeader> headers = new ArrayList<>();
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String key = (String) table.getValueAt(i, 1);
            String value = (String) table.getValueAt(i, 2);
            headers.add(new EmailHeader(key, value));
        }
        return headers;
    }
}
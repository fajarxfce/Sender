package com.anjay.mabar.models;

import org.simplejavamail.api.email.ContentTransferEncoding;

import javax.swing.*;

public class SendingConfig {
    private JSpinner connectionCount;
    private JSpinner threadCount;
    private JSpinner sleepTime;
    private JComboBox mailPriority;
    private JComboBox<ContentTransferEncoding> encoding;

    public SendingConfig(JSpinner connectionCount, JSpinner threadCount, JSpinner sleepTime, JComboBox mailPriority, JComboBox<ContentTransferEncoding> encoding) {
        this.connectionCount = connectionCount;
        this.threadCount = threadCount;
        this.sleepTime = sleepTime;
        this.mailPriority = mailPriority;
        this.encoding = encoding;
    }

    public JSpinner getConnectionCount() {
        return connectionCount;
    }

    public JSpinner getThreadCount() {
        return threadCount;
    }

    public JSpinner getSleepTime() {
        return sleepTime;
    }

    public JComboBox getMailPriority() {
        return mailPriority;
    }

    public JComboBox<ContentTransferEncoding> getEncoding() {
        return encoding;
    }
}

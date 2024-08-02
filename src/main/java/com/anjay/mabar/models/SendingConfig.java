package com.anjay.mabar.models;

import javax.swing.*;

public class SendingConfig {
    private JSpinner connectionCount;
    private JSpinner threadCount;
    private JSpinner sleepTime;
    private JComboBox mailPriority;

    public SendingConfig(JSpinner connectionCount, JSpinner threadCount, JSpinner sleepTime, JComboBox mailPriority) {
        this.connectionCount = connectionCount;
        this.threadCount = threadCount;
        this.sleepTime = sleepTime;
        this.mailPriority = mailPriority;
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
}

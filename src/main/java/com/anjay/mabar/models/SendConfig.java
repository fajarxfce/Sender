package com.anjay.mabar.models;

public class SendConfig {
    private String connectionCount;
    private String threadCount;
    private String sleepTime;
    private int mailPriority;

    public SendConfig(String connectionCount, String threadCount, String sleepTime, int mailPriority) {
        this.connectionCount = connectionCount;
        this.threadCount = threadCount;
        this.sleepTime = sleepTime;
        this.mailPriority = mailPriority;
    }

    public String getConnectionCount() {
        return connectionCount;
    }

    public String getThreadCount() {
        return threadCount;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public int getMailPriority() {
        return mailPriority;
    }
}

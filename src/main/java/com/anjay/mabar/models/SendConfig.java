package com.anjay.mabar.models;

import org.simplejavamail.api.email.ContentTransferEncoding;

public class SendConfig {
    private String connectionCount;
    private String threadCount;
    private String sleepTime;
    private int mailPriority;
    private ContentTransferEncoding contentTransferEncoding;

    public SendConfig(String connectionCount, String threadCount, String sleepTime, int mailPriority, ContentTransferEncoding contentTransferEncoding) {
        this.connectionCount = connectionCount;
        this.threadCount = threadCount;
        this.sleepTime = sleepTime;
        this.mailPriority = mailPriority;
        this.contentTransferEncoding = contentTransferEncoding;
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

    public ContentTransferEncoding getContentTransferEncoding() {
        return contentTransferEncoding;
    }
}

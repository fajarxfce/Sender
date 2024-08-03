package com.anjay.mabar.models;

import org.simplejavamail.api.email.ContentTransferEncoding;
import java.util.List;

public class ContentDetails {
    private String subject;
    private String body;
    private String messageId;
    private List<EmailHeader> headers;
    private int priority;
    private ContentTransferEncoding encoding;
    private String contentType;

    // Getters and Setters


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public List<EmailHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<EmailHeader> headers) {
        this.headers = headers;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ContentTransferEncoding getEncoding() {
        return encoding;
    }

    public void setEncoding(ContentTransferEncoding encoding) {
        this.encoding = encoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
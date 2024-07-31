package com.anjay.mabar.models;

public class EmailDetails {
    private String subject;
    private String fromName;
    private String body;

    public EmailDetails(String subject, String fromName, String body) {
        this.subject = subject;
        this.fromName = fromName;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

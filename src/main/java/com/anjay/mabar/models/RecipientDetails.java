package com.anjay.mabar.models;

public class RecipientDetails {
    private String targetEmail;
    private String fromName;
    private String replyTo;
    private String bounceTo;

    // Getters and Setters


    public String getTargetEmail() {
        return targetEmail;
    }

    public void setTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getBounceTo() {
        return bounceTo;
    }

    public void setBounceTo(String bounceTo) {
        this.bounceTo = bounceTo;
    }
}
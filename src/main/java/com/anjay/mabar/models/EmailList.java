package com.anjay.mabar.models;

public class EmailList {
    private String emailAddress;

    public EmailList(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Getter and setter
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
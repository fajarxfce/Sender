package com.anjay.mabar.models;

import java.util.List;

public class EmailDetails {
    private List<String> subject;
    private List<String> fromName;
    private String body;
    private String toAddress;

    private EmailDetails(Builder builder) {
        this.subject = builder.subject;
        this.fromName = builder.fromName;
        this.toAddress = builder.toAddress;
        this.body = builder.body;
    }

    public static class Builder {
        private List<String> subject;
        private List<String> fromName;
        private String body;
        private String toAddress;

        public Builder setSubject(List<String> subject) {
            this.subject = subject;
            return this;
        }

        public Builder setFromName(List<String> fromName) {
            this.fromName = fromName;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setToAddress(String toAddress) {
            this.toAddress = toAddress;
            return this;
        }
        public EmailDetails build() {
            return new EmailDetails(this);
        }
    }

    public List<String> getSubject() {
        return subject;
    }

    public List<String> getFromName() {
        return fromName;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getBody() {
        return body;
    }
}
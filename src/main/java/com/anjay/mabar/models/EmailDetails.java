package com.anjay.mabar.models;

public class EmailDetails {
    private String subject;
    private String fromName;
    private String body;

    private EmailDetails(Builder builder) {
        this.subject = builder.subject;
        this.fromName = builder.fromName;
        this.body = builder.body;
    }

    public static class Builder {
        private String subject;
        private String fromName;
        private String body;

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setFromName(String fromName) {
            this.fromName = fromName;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public EmailDetails build() {
            return new EmailDetails(this);
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getFromName() {
        return fromName;
    }

    public String getBody() {
        return body;
    }
}
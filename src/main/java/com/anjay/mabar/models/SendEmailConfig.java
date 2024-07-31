package com.anjay.mabar.models;

import com.anjay.mabar.controllers.ImportListController;

import javax.swing.*;

public class SendEmailConfig {
    private EmailListTable emailListTable;
    private SMTPTableModel smtpTableModel;
    private ImportListController importListController;
    private int connectionCount;
    private JTextArea textAreaSubject;
    private JTextArea textAreaFromName;
    private JTextArea textAreaBody;

    private SendEmailConfig(Builder builder) {
        this.emailListTable = builder.emailListTable;
        this.smtpTableModel = builder.smtpTableModel;
        this.importListController = builder.importListController;
        this.connectionCount = builder.connectionCount;
        this.textAreaSubject = builder.textAreaSubject;
        this.textAreaFromName = builder.textAreaFromName;
        this.textAreaBody = builder.textAreaBody;
    }

    public static class Builder {
        private EmailListTable emailListTable;
        private SMTPTableModel smtpTableModel;
        private ImportListController importListController;
        private int connectionCount;
        private JTextArea textAreaSubject;
        private JTextArea textAreaFromName;
        private JTextArea textAreaBody;

        public Builder setEmailListTable(EmailListTable emailListTable) {
            this.emailListTable = emailListTable;
            return this;
        }

        public Builder setSmtpTableModel(SMTPTableModel smtpTableModel) {
            this.smtpTableModel = smtpTableModel;
            return this;
        }

        public Builder setImportListController(ImportListController importListController) {
            this.importListController = importListController;
            return this;
        }

        public Builder setConnectionCount(int connectionCount) {
            this.connectionCount = connectionCount;
            return this;
        }

        public Builder setTextAreaSubject(JTextArea textAreaSubject) {
            this.textAreaSubject = textAreaSubject;
            return this;
        }

        public Builder setTextAreaFromName(JTextArea textAreaFromName) {
            this.textAreaFromName = textAreaFromName;
            return this;
        }

        public Builder setTextAreaBody(JTextArea textAreaBody) {
            this.textAreaBody = textAreaBody;
            return this;
        }

        public SendEmailConfig build() {
            return new SendEmailConfig(this);
        }
    }

    public EmailListTable getEmailListTable() {
        return emailListTable;
    }

    public SMTPTableModel getSmtpTableModel() {
        return smtpTableModel;
    }

    public ImportListController getImportListController() {
        return importListController;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public JTextArea getTextAreaSubject() {
        return textAreaSubject;
    }

    public JTextArea getTextAreaFromName() {
        return textAreaFromName;
    }

    public JTextArea getTextAreaBody() {
        return textAreaBody;
    }
}
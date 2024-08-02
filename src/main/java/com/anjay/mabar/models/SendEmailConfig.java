package com.anjay.mabar.models;

import com.anjay.mabar.controllers.ImportListController;

import javax.swing.*;
import java.awt.*;

public class SendEmailConfig {
    private EmailListTable emailListTable;
    private SMTPTableModel smtpTableModel;
    private ImportListController importListController;
    private int connectionCount;
    private JTextArea textAreaSubject;
    private JTextArea textAreaFromName;
    private JTextArea textAreaBody;
    private String contentType;
    private EmailHeaderTable headerTable;
    private JTextField messageID;
    private JTextField txtReplyTo;
    private JTextField txtBounceTo;

    private SendEmailConfig(Builder builder) {
        this.emailListTable = builder.emailListTable;
        this.smtpTableModel = builder.smtpTableModel;
        this.importListController = builder.importListController;
        this.connectionCount = builder.connectionCount;
        this.textAreaSubject = builder.textAreaSubject;
        this.textAreaFromName = builder.textAreaFromName;
        this.textAreaBody = builder.textAreaBody;
        this.contentType = builder.contentType;
        this.headerTable = builder.headerTable;
        this.messageID = builder.messageID;
        this.txtReplyTo = builder.txtReplyTo;
        this.txtBounceTo = builder.txtBounceTo;
    }

    public static class Builder {
        private EmailListTable emailListTable;
        private SMTPTableModel smtpTableModel;
        private ImportListController importListController;
        private int connectionCount;
        private JTextArea textAreaSubject;
        private JTextArea textAreaFromName;
        private JTextArea textAreaBody;
        private String contentType;
        private EmailHeaderTable headerTable;
        private JTextField messageID;
        private JTextField txtReplyTo;
        private JTextField txtBounceTo;
        public Builder setEmailListTable(EmailListTable emailListTable) {
            this.emailListTable = emailListTable;
            return this;
        }

        public Builder setSmtpTableModel(SMTPTableModel smtpTableModel) {
            this.smtpTableModel = smtpTableModel;
            return this;
        }

        public Builder setListController(ImportListController importListController) {
            this.importListController = importListController;
            return this;
        }

        public Builder setConnection(int connectionCount) {
            this.connectionCount = connectionCount;
            return this;
        }

        public Builder setSubject(JTextArea textAreaSubject) {
            this.textAreaSubject = textAreaSubject;
            return this;
        }

        public Builder setFromName(JTextArea textAreaFromName) {
            this.textAreaFromName = textAreaFromName;
            return this;
        }

        public Builder setBody(JTextArea textAreaBody) {
            this.textAreaBody = textAreaBody;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setHeaderTable(EmailHeaderTable headerTable) {
            this.headerTable = headerTable;
            return this;
        }

        public Builder setMessageID(JTextField messageID) {
            this.messageID = messageID;
            return this;
        }

        public Builder setTxtReplyTo(JTextField txtReplyTo) {
            this.txtReplyTo = txtReplyTo;
            return this;
        }

        public Builder setTxtBounceTo(JTextField txtBounceTo) {
            this.txtBounceTo = txtBounceTo;
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

    public String getContentType() {
        return contentType;
    }

    public JTextField getMessageID() {
        return messageID;
    }
    public EmailHeaderTable getHeaderTable() {
        return headerTable;
    }

    public JTextField getTxtReplyTo() {
        return txtReplyTo;
    }

    public JTextField getTxtBounceTo() {
        return txtBounceTo;
    }
}
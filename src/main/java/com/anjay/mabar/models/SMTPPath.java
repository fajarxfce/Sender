package com.anjay.mabar.models;

import javax.swing.*;

public class SMTPPath {
    private JLabel smtpCount;
    public SMTPPath(JLabel smtpCount) {
        this.smtpCount = smtpCount;
    }
    public void setPath(String path) {
        System.out.println(path);
    }
    public void setCount(int count) {
        smtpCount.setText(String.valueOf(count));
    }
}

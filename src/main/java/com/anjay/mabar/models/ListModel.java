package com.anjay.mabar.models;

import javax.swing.*;

public class ListModel {
    private JLabel emailCount;
    public ListModel(JLabel emailCount) {
        this.emailCount = emailCount;
    }
    public void setPath(String path) {
        System.out.println(path);
    }
    public void setCount(int count) {
        emailCount.setText(String.valueOf(count));
    }
}

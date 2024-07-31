package com.anjay.mabar.models;

import javax.swing.table.DefaultTableModel;

public class SMTPTableModel extends DefaultTableModel {

    public SMTPTableModel() {
        super(new Object[]{"No", "Email Address", "Username", "Password"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Make cells non-editable
    }
}
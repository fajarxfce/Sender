package com.anjay.mabar.models;

import javax.swing.table.DefaultTableModel;

public class EmailHeaderTable extends DefaultTableModel {

    public EmailHeaderTable() {
        super(new Object[]{"No", "X-header", "Data"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true; // Make cells non-editable
    }
}

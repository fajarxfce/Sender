package com.anjay.mabar.view;

import com.anjay.mabar.controllers.ImportListController;
import com.anjay.mabar.controllers.SelectSMTPController;
import com.anjay.mabar.controllers.SendMainController;
import com.anjay.mabar.models.*;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.TableColumn;

public class Latihan extends JFrame {
    private JPanel root;
    private JPanel smtpPane;
    private JPanel leftPane;
    private JPanel righPane;
    private JPanel middlePane;
    private JPanel fromPane;
    private JPanel headerPane;
    private JPanel emailListPane;
    private JPanel letterPane;
    private JPanel letterPanes;
    private JPanel sendPane;
    private JRadioButton HTMLRadioButton;
    private JRadioButton plaintTextRadioButton;
    private JTextArea textAreaBody;
    private JSpinner spConnection;
    private JSpinner spPause;
    private JSpinner spSleep;
    private JButton stopButton;
    private JButton startButton;
    private JLabel smtpCount;
    private JButton editButton;
    private JButton importSmtp;
    private JTable smtpTable;
    private JTabbedPane fromTab;
    private JPanel subjectPane;
    private JPanel fromNamePane;
    private JPanel fromMailPane;
    private JTextArea textAreaSubject;
    private JTextArea textAreaFromName;
    private JTextArea textAreaFromMail;
    private JButton addList;
    private JButton clearAllButton;
    private JButton clearSuccessButton;
    private JTable tableEmailList;
    private JTable table2;
    private JTable table3;
    private JScrollPane jScrollSmtp;
    private JLabel txtListCount;
    private JButton clearButton;
    private JButton importButton;
    private ImportListController importListController;
    private ButtonGroup letterMode;

    public Latihan(){
        add(root);

        fromTab.addTab("Subject", subjectPane);
        fromTab.addTab("From Name", fromNamePane);
        fromTab.addTab("From Mail", fromMailPane);

        letterMode = new ButtonGroup();
        letterMode.add(HTMLRadioButton);
        letterMode.add(plaintTextRadioButton);

        SMTPTableModel smtpTableModel = new SMTPTableModel();
        smtpTable.setModel(smtpTableModel);
        SMTPPath smtpPath = new SMTPPath(smtpCount);
        SelectSMTPController selectSMTPController = new SelectSMTPController(smtpPath, smtpTableModel);
        importSmtp.addActionListener(selectSMTPController);
        spConnection.setValue(2);
        spSleep.setValue(3000);

        EmailListPath emailListPath = new EmailListPath(txtListCount);
        EmailListTable emailListTable = new EmailListTable();
        tableEmailList.setModel(emailListTable);
        importListController = new ImportListController(emailListTable);
        addList.addActionListener(importListController);

        String contentType = HTMLRadioButton.isSelected() ? "html" : "text";

        SendEmailConfig sendEmailConfig = new SendEmailConfig.Builder()
                .setEmailListTable(emailListTable)
                .setSmtpTableModel(smtpTableModel)
                .setListController(importListController)
                .setConnection((int) spConnection.getValue())
                .setSubject(textAreaSubject)
                .setFromName(textAreaFromName)
                .setContentType(contentType)
                .setBody(textAreaBody)
                .build();

        SendMainController sendMainController = new SendMainController(sendEmailConfig);
        startButton.addActionListener(sendMainController);
        stopButton.addActionListener(sendMainController);

        int[] columnWidths = {50, 200, 150};
        setColumnWidths(smtpTable, columnWidths);

    }

    private void setColumnWidths(JTable table, int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }

    public static void main(String[] args) {
        setupLookAndFeel();
        Latihan main = new Latihan();
        main.setSize(1200, 800);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }

    private static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
            JOptionPane.showMessageDialog(null, "Failed to initialize LaF");
        }
    }
}

package com.anjay.mabar.view;

import com.anjay.mabar.controllers.CustomHeaderController;
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
    private JTable headerTable;
    private JTable table3;
    private JScrollPane jScrollSmtp;
    private JLabel txtListCount;
    private JButton clearHeader;
    private JButton importHeader;
    private JButton addHeader;
    private JButton saveHeader;
    private JTextField txtMessageId;
    private JComboBox priority;
    private ImportListController importListController;
    private ButtonGroup letterMode;

    public Latihan(){
        add(root);

        fromTab.addTab("Subject", subjectPane);
        fromTab.addTab("From Name", fromNamePane);
        fromTab.addTab("From Mail", fromMailPane);

        priority.addItem("Highest");
        priority.addItem("High");
        priority.addItem("Normal");
        priority.addItem("Low");
        priority.addItem("Lowest");
        priority.setSelectedIndex(2);

        letterMode = new ButtonGroup();
        letterMode.add(HTMLRadioButton);
        letterMode.add(plaintTextRadioButton);

        EmailHeaderTable emailHeaderTable = new EmailHeaderTable();
        headerTable.setModel(emailHeaderTable);

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
                .setHeaderTable(emailHeaderTable)
                .setListController(importListController)
                .setConnection((int) spConnection.getValue())
                .setSubject(textAreaSubject)
                .setFromName(textAreaFromName)
                .setMessageID(txtMessageId)
                .setContentType(contentType)
                .setBody(textAreaBody)
                .build();

        SendingConfig sendingConfig = new SendingConfig(spConnection, spSleep, spPause, priority);

        SendMainController sendMainController = new SendMainController(sendEmailConfig, sendingConfig);
        startButton.addActionListener(sendMainController);
        stopButton.addActionListener(sendMainController);

        int[] columnWidths = {50, 200, 150};
        setColumnWidths(smtpTable, columnWidths);

        CustomHeaderController headerController = new CustomHeaderController(emailHeaderTable, headerTable);
        addHeader.addActionListener(headerController);
        importHeader.addActionListener(headerController);
        clearHeader.addActionListener(headerController);
        saveHeader.addActionListener(headerController);

        createSubject();
        createFromName();
        createFromMail();

    }

    private void setColumnWidths(JTable table, int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }

    private void createSubject(){
        textAreaSubject.append("Just test my mailrand str : ##randstr28##\n");
        textAreaSubject.append("Subject lagi : ##randstr28##\n");
        textAreaSubject.append("Test Subject : ##randstr28##\n");
        textAreaSubject.append("Subject coy : ##randstr28##\n");
    }

    private void createFromName(){
        textAreaFromName.append("Pajar Tampan\n");
        textAreaFromName.append("Pajar Ganteng\n");
        textAreaFromName.append("Kirik\n");
        textAreaFromName.append("Riski\n");
        textAreaFromName.append("Fajar\n");
        textAreaFromName.append("Jokowi\n");
        textAreaFromName.append("Pajar Anjay\n");
    }

    private void createFromMail() {
        textAreaBody.append("rand str : ##randstr28##\n" +
                "rand num : ##randnum10##\n" +
                "rand str upper : ##randupper30##\n" +
                "rand str lower : ##randlower23##\n" +
                "rand aes : ##randaes##\n");
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

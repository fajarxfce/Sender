package com.anjay.mabar.view;

import com.anjay.mabar.controllers.ImportListController;
import com.anjay.mabar.controllers.SelectSMTPController;
import com.anjay.mabar.controllers.SendMainController;
import com.anjay.mabar.models.*;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    private JPanel root;
    private JPanel leftPane;
    private JPanel rightPane;
    private JPanel smtpPane;
    private JPanel fromPane;
    private JPanel emailListPane;
    private JPanel letterPane;
    private JPanel letterPanes;
    private JPanel sendPane;
    private JRadioButton HTMLRadioButton;
    private JRadioButton plaintTextRadioButton;
    private JTextArea textAreaBody;
    private JButton importSmtp;
    private JButton editButton;
    private JTable smtpTable;
    private JSpinner spConnection;
    private JSpinner spPause;
    private JSpinner spSleep;
    private JButton startButton;
    private JTabbedPane fromTab;
    private JPanel subjectPane;
    private JPanel fromNamePane;
    private JPanel fromMailPane;
    private JButton addList;
    private JButton clearAllButton;
    private JButton clearSuccessButton;
    private JTable tableEmailList;
    private JToolBar toolbar;
    private JLabel smtpCount;
    private JLabel txtListCount;
    private JButton stopButton;
    private JTextArea textAreaSubject;
    private JTextArea textAreaFromName;
    private JTextArea textAreaFromMail;
    private ImportListController importListController;
    private ButtonGroup letterMode;

    public Main() {
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

        SendEmailConfig sendEmailConfig = new SendEmailConfig.Builder()
                .setEmailListTable(emailListTable)
                .setSmtpTableModel(smtpTableModel)
                .setImportListController(importListController)
                .setConnectionCount((int) spConnection.getValue())
                .setTextAreaSubject(textAreaSubject)
                .setTextAreaFromName(textAreaFromName)
                .setTextAreaBody(textAreaBody)
                .build();

        SendMainController sendMainController = new SendMainController(sendEmailConfig);
        startButton.addActionListener(sendMainController);
        stopButton.addActionListener(sendMainController);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 1; i <= 1000; i++) {
                    System.out.println("email" + i + "@gmail.com");
                }
            }
        });
    }

    public static void main(String[] args) {
        setupLookAndFeel();
        Main main = new Main();
        main.setSize(800, 600);
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

package com.anjay.mabar.view;

import com.anjay.mabar.controllers.ImportListController;
import com.anjay.mabar.controllers.SelectSMTPController;
import com.anjay.mabar.models.*;
import com.anjay.mabar.utils.EmailSender;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    private JTextArea textArea1;
    private JButton importSmtp;
    private JButton editButton;
    private JTable smtpTable;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JButton STOPButton;
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
    private ImportListController importListController;

    public Main() {
        add(root);

        SMTPTableModel smtpTableModel = new SMTPTableModel();
        smtpTable.setModel(smtpTableModel);
        SMTPPath smtpPath = new SMTPPath(smtpCount);
        SelectSMTPController selectSMTPController = new SelectSMTPController(smtpPath, smtpTableModel);
        importSmtp.addActionListener(selectSMTPController);

        EmailListPath emailListPath = new EmailListPath(txtListCount);
        EmailListTable emailListTable = new EmailListTable();
        tableEmailList.setModel(emailListTable);
        importListController = new ImportListController(emailListTable);
        addList.addActionListener(importListController);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEmails();
            }
        });
    }
    private void sendEmails() {
        SMTPTableModel smtpTableModel = (SMTPTableModel) smtpTable.getModel();
        int rowCount = smtpTableModel.getRowCount();
        List<String> bccAddresses = importListController.getEmailAddresses();

        for (int i = 0; i < rowCount; i++) {
            String host = "smtp.gmail.com";
            String port = "587";
            String username = (String) smtpTableModel.getValueAt(i, 2);
            String password = (String) smtpTableModel.getValueAt(i, 3);
            String toAddress = "recipient-email@gmail.com";
            String subject = "Test Email";
            String message = "This is a test email sent from Java.";

            for (String bccAddress : bccAddresses) {
                EmailSender.sendEmail(host, port, username, password, toAddress, bccAddress, subject, message);
            }
        }
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

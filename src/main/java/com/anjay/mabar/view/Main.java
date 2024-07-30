package com.anjay.mabar.view;

import com.anjay.mabar.controllers.SelectSMTPController;
import com.anjay.mabar.models.EmailList;
import com.anjay.mabar.models.SMTPPath;
import com.anjay.mabar.models.SMTPTableModel;
import com.anjay.mabar.models.Smtp;
import com.anjay.mabar.utils.CsvReader;
import com.anjay.mabar.utils.TxtReader;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
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
    private JButton STARTButton;
    private JTabbedPane fromTab;
    private JPanel subjectPane;
    private JPanel fromNamePane;
    private JPanel fromMailPane;
    private JButton addButton;
    private JButton clearAllButton;
    private JButton clearSuccessButton;
    private JTable table1;
    private JToolBar toolbar;
    private JLabel smtpCount;

    public Main() {
        add(root);

        SMTPTableModel smtpTableModel = new SMTPTableModel();
        smtpTable.setModel(smtpTableModel);
        SMTPPath smtpPath = new SMTPPath(smtpCount);
        SelectSMTPController selectSMTPController = new SelectSMTPController(smtpPath, smtpTableModel);
        importSmtp.addActionListener(selectSMTPController);

    }

    private void loadSmtp(){
        String filePath = "C:\\Users\\Fajar\\Downloads\\smtp.csv";
        List<Smtp> smtps = CsvReader.readCsv(filePath);

        for (Smtp smtp : smtps) {
            System.out.println("First Name: " + smtp.getFirstName());
            System.out.println("Last Name: " + smtp.getLastName());
            System.out.println("Email Address: " + smtp.getEmailAddress());
            System.out.println("Password: " + smtp.getPassword());
        }

        String emailListPath = "D:\\to linux\\list\\webde.txt";
        List<EmailList> emailLists = TxtReader.readTxt(emailListPath);

        for (EmailList emailList : emailLists) {
            System.out.println("Email Address: " + emailList.getEmailAddress());
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

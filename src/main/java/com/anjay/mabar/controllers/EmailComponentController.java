package com.anjay.mabar.controllers;

import com.anjay.mabar.models.EmailComponent;
import com.anjay.mabar.utils.TxtReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmailComponentController implements ActionListener {

    private EmailComponent emailComponent;

    public EmailComponentController(EmailComponent emailComponent) {
        this.emailComponent = emailComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Import":
                onImport();
            case "Save":
//                onSave();
                break;

        }
    }

    private void onSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Email Components");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("#FROMNAME");
                writer.newLine();
                writer.write(emailComponent.getFromName().getText());
                writer.newLine();

                writer.write("#SUBJECT");
                writer.newLine();
                writer.write(emailComponent.getSubject().getText());
                writer.newLine();

                writer.write("#FROMMAIL");
                writer.newLine();
                writer.write(emailComponent.getFromMail().getText());
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void onImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import Email Components");

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
                String line;
                String currentTag = "";
                List<String> fromNames = new ArrayList<>();
                List<String> subjects = new ArrayList<>();
                List<String> fromMails = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("#")) {
                        currentTag = line;
                    } else {
                        switch (currentTag) {
                            case "#FROMNAME":
                                fromNames.add(line);
                                break;
                            case "#SUBJECT":
                                subjects.add(line);
                                break;
                            case "#FROMMAIL":
                                fromMails.add(line);
                                break;
                        }
                    }
                }

                for (String fromName : fromNames) {
                    System.out.println(fromName);
                }

                // Assuming you want to set the first value of each list to the EmailComponent
                if (!fromNames.isEmpty()) {
                    emailComponent.setFromName(fromNames);
                }
                if (!subjects.isEmpty()) {
                    emailComponent.setSubject(subjects);
                }
                if (!fromMails.isEmpty()) {
                    emailComponent.setFromMail(fromMails);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setEmailComponents(List<EmailComponent> emailComponents) {
        // Implement this method to set the list of email components to your model
    }
}

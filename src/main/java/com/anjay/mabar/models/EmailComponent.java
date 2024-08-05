package com.anjay.mabar.models;

import javax.swing.*;
import java.util.List;

public class EmailComponent {
    private JTextArea fromName;
    private JTextArea fromMail;
    private JTextArea subject;

    public EmailComponent(JTextArea fromName, JTextArea fromMail, JTextArea subject) {
        this.fromName = fromName;
        this.fromMail = fromMail;
        this.subject = subject;
    }

    public JTextArea getFromName() {
        return fromName;
    }

    public JTextArea getFromMail() {
        return fromMail;
    }

    public JTextArea getSubject() {
        return subject;
    }

    public void setFromName (List<String> fromName){
        this.fromName.setText("");
        for (String name : fromName){
            this.fromName.append(name + "\n");
        }
    }

    public void setFromMail (List<String> fromMail){
        this.fromMail.setText("");
        for (String mail : fromMail){
            this.fromMail.append(mail + "\n");
        }
    }

    public void setSubject (List<String> subject){
        this.subject.setText("");
        for (String subj : subject){
            this.subject.append(subj + "\n");
        }
    }
}

package com.anjay.mabar.utils;

import com.anjay.mabar.models.EmailHeaderTable;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(final String username, final String password, String fromName, String toAddress, String bccAddress, String subject, String message) {
        // Set properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", Config.SMTP_HOST);
        properties.put("mail.smtp.port", Config.SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {



            // Create a new email message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username, fromName));

            File selectedFile = new File("C:\\Users\\Fajar\\Documents\\sender\\headers.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        msg.addHeader(parts[0], parts[1]);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println(msg.getAllHeaders());;

            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            if (bccAddress != null && !bccAddress.isEmpty()) {
                msg.setRecipient(Message.RecipientType.BCC, new InternetAddress(bccAddress));
            }
            msg.setSubject(subject);
            msg.setText(message);

            // Send the email
            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("Failed to send email "+bccAddress+" with smtp"+ username);
            System.out.println("Subject: "+subject);
            System.out.println("Subject: "+message);
        }
    }
}
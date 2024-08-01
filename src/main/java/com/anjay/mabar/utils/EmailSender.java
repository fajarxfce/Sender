package com.anjay.mabar.utils;

import com.anjay.mabar.models.EmailHeaderTable;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(final String username, final String password, String fromName, String toAddress, String bccAddress, String subject, String message, String contentType) throws MessagingException, UnsupportedEncodingException {
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
        // Create a new email message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username, fromName));
        msg.setContent("Content-Type", "text/html; charset=UTF-8");

        File selectedFile = new File("C:\\Users\\Fajar\\Documents\\sender\\headers.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    msg.addHeader(StringUtil.replacePlaceholders(parts[0]), StringUtil.replacePlaceholders(parts[1]));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        if (bccAddress != null && !bccAddress.isEmpty()) {
            msg.setRecipient(Message.RecipientType.BCC, new InternetAddress(bccAddress));
        }
        msg.setSubject(StringUtil.replacePlaceholders(subject));
//            msg.setText(StringUtil.replacePlaceholders(message));
        msg.setContent(
                "<h1>This is actual message embedded in HTML tags</h1>",
                "text/html");
        // Send the email
        Transport.send(msg);

    }
}
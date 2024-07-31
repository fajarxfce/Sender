package com.anjay.mabar.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static final String username = "";
    private static final String bccAddress = "";

    public static void sendEmail(final String username, final String password, String toAddress, String bccAddress, String subject, String message) {
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
            msg.setFrom(new InternetAddress(username));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            if (bccAddress != null && !bccAddress.isEmpty()) {
                msg.setRecipient(Message.RecipientType.BCC, new InternetAddress(bccAddress));
            }
            msg.setSubject(subject);
            msg.setText(message);

            // Send the email
            Transport.send(msg);
//            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email "+bccAddress+" with smtp"+ username);

//            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
    }
}
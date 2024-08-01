// SimpleMail.java
package com.anjay.mabar.utils;

import jakarta.mail.MessagingException;
import org.simplejavamail.api.email.ContentTransferEncoding;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SimpleMail {

    public static void sendMail(String targetEmail, String username, String password, String fromName, String subject, String body) throws MessagingException, IOException {

//        Calendar icsCalendar = new Calendar();
//        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
//        icsCalendar.getProperties().add(Version.VERSION_2_0);
//
//         Produce calendar string
//        ByteArrayOutputStream bOutStream = new ByteArrayOutputStream();
//        new CalendarOutputter().output(icsCalendar, bOutStream);
//        String yourICalEventString = bOutStream.toString("UTF-8");

        Email email = EmailBuilder.startingBlank()
                .from(fromName, username)
                .bcc(targetEmail)
                .withContentTransferEncoding(ContentTransferEncoding.BIT8)
                .withSubject(StringUtil.replacePlaceholders(subject))
                .withHeader("X-Csa-Complaints", "csa-complaints@eco.de")
                .withHeader("X-Mailer", "Sendinblue")
                .withHeader("X-Mailin-Campaign", StringUtil.replacePlaceholders("##randnum10##"))
                .withHeader("X-Mailin-Client", StringUtil.replacePlaceholders("##randnum12##"))
                .withHeader("X-sib-id", StringUtil.replacePlaceholders("##randaes##"))
                .withBounceTo("bounce@" + "techmatra.com")
                .withReplyTo("no-reply@techmatra.com")
                .fixingMessageId(generateMessageId())
//                .withCalendarText(CalendarMethod.REQUEST, yourICalEventString)
                .withHTMLText(StringUtil.replacePlaceholders(body))
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, username, password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        mailer.shutdownConnectionPool();

    }

    private static String generateMessageId() {
        return "<" + UUID.randomUUID().toString() + "@microsoft.com>";
    }

//    public static void main(String[] args) {
//        try {
//            sendMail("cirebonredhat@gmail.com",
//                    "noerappp123212121212121211221@techmatra.com",
//                    "kontol123@",
//                    "=?UTF-8?B?YW5qYXkgbWFtYW5r?=",
//                    "hallo fajar"
//            );
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
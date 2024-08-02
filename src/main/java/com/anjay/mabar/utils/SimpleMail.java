// SimpleMail.java
package com.anjay.mabar.utils;

import com.anjay.mabar.models.EmailHeader;
import jakarta.mail.MessagingException;
import org.simplejavamail.api.email.ContentTransferEncoding;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import java.io.IOException;
import java.util.*;


public class SimpleMail {

    public static void sendMail(String targetEmail, String username, String password, String fromName, String subject, String body, String messageId, List<EmailHeader> headers, int priority) throws MessagingException, IOException {

//        Calendar icsCalendar = new Calendar();
//        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
//        icsCalendar.getProperties().add(Version.VERSION_2_0);
//
//         Produce calendar string
//        ByteArrayOutputStream bOutStream = new ByteArrayOutputStream();
//        new CalendarOutputter().output(icsCalendar, bOutStream);
//        String yourICalEventString = bOutStream.toString("UTF-8");
        Map<String, Collection<String>> headerMap = new HashMap<>();

        for (EmailHeader header : headers) {
            headerMap.put(StringUtil.replacePlaceholders(header.getHeaderName()), Collections.singletonList(StringUtil.replacePlaceholders(header.getHeaderValue())));
        }

        Email email = EmailBuilder.startingBlank()
                .from(fromName, username)
                .bcc(targetEmail)
                .withContentTransferEncoding(ContentTransferEncoding.BIT8)
                .withSubject(StringUtil.replacePlaceholders(subject))
                .withHeader("X-Priority", priority)
                .withHeaders(headerMap)
                .withBounceTo("bounce@" + "techmatra.com")
                .withReplyTo("no-reply@techmatra.com")
                .fixingMessageId(generateMessageId(messageId))
                .withHTMLText(StringUtil.replacePlaceholders(body))
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, username, password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();

        mailer.sendMail(email);
        mailer.shutdownConnectionPool();

    }

    private static String generateMessageId(String domain) {
        return "<" + UUID.randomUUID().toString() + "@"+domain+">";
    }
}
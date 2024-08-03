// SimpleMail.java
package com.anjay.mabar.utils;

import com.anjay.mabar.models.EmailHeader;
import com.anjay.mabar.models.MailDetails;
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

    public static void sendMail(MailDetails mailDetails) throws MessagingException, IOException {

        String username = mailDetails.getServerDetails().getUsername();
        String password = mailDetails.getServerDetails().getPassword();
        String targetEmail = mailDetails.getRecipientDetails().getTargetEmail();
        List<EmailHeader> headers = mailDetails.getContentDetails().getHeaders();
        String fromName = mailDetails.getRecipientDetails().getFromName();
        String subject = mailDetails.getContentDetails().getSubject();
        String body = mailDetails.getContentDetails().getBody();
        ContentTransferEncoding encoding = mailDetails.getContentDetails().getEncoding();
        String messageId = mailDetails.getContentDetails().getMessageId();
        String replyTo = mailDetails.getRecipientDetails().getReplyTo();
        String bounceTo = mailDetails.getRecipientDetails().getBounceTo();
        int priority = mailDetails.getContentDetails().getPriority();



        Map<String, Collection<String>> headerMap = new HashMap<>();

        for (EmailHeader header : headers) {
            headerMap.put(StringUtil.replacePlaceholders(header.getHeaderName()), Collections.singletonList(StringUtil.replacePlaceholders(header.getHeaderValue())));
        }

        Email email = EmailBuilder.startingBlank()
                .from(StringUtil.replacePlaceholders(fromName), username)
                .bcc(targetEmail)
                .withContentTransferEncoding(encoding)
                .withSubject(StringUtil.replacePlaceholders(subject))
                .withHeader("X-Priority", priority)
                .withHeaders(headerMap)
                .withBounceTo(StringUtil.replacePlaceholders(bounceTo))
                .withReplyTo(StringUtil.replacePlaceholders(replyTo))
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

//    public static void main(String[] args) throws MessagingException, IOException {
//        List<EmailHeader> headers = new ArrayList<>();
//        headers.add(new EmailHeader("X-Mailer", "Sendinblue"));
//
//        SimpleMail.sendMail(
//                "cirebonredhat@gmail.com",
//                "noerappp123212121212121211221@techmatra.com",
//                "kontol123@" ,
//                "Fajar",
//                "this is test mail",
//                "hallo fajar",
//                "google.com",
//                headers,
//                1);
//    }
}
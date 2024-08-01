package com.anjay.mabar.worker;

import com.anjay.mabar.models.EmailDetails;
import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.utils.EmailSender;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendEmailWorker extends SwingWorker<Void, String> {
    private ExecutorService executorService;
    private List<String> emailList;
    private List<SendMailObserver> observers;
    private int connectionCount;
    private List<SMTPServer> smtpServers;
    private EmailDetails emailDetails;
    public SendEmailWorker(List<String> emailList, List<SMTPServer> smtpServers, int connectionCount, EmailDetails emailDetails) {
        this.emailList = emailList;
        this.smtpServers = smtpServers;
        this.connectionCount = connectionCount;
        this.emailDetails = emailDetails;
        this.observers = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    protected Void doInBackground() throws Exception {
        System.out.println("Email list size: " + emailList.size());
        System.out.println("SMTP Server size: " + smtpServers.size());

        int smtpIndex = 0;
        for (int i = 0; i < emailList.size(); i += 1) {
            SMTPServer smtpServer = smtpServers.get(smtpIndex);
            for (int j = 0; j < 1 && (i + j) < emailList.size(); j++) {
                String email = emailList.get(i + j);
                executorService.submit(() -> {
                    try {

                        List<String> subjectList = emailDetails.getSubject();
                        Collections.shuffle(subjectList);
                        String subject = subjectList.get(0);

                        List<String> fromNameList = emailDetails.getFromName();
                        Collections.shuffle(fromNameList);
                        String fromName = fromNameList.get(0);

                        String body = emailDetails.getBody();
                        String toAddress = emailDetails.getToAddress();
                        String contentType = emailDetails.getContentType();

                        EmailSender.sendEmail(
                                smtpServer.getUsername(),
                                smtpServer.getPassword(),
                                fromName,
                                "support@mail.google.com",
                                email,
                                subject,
                                body,
                                contentType
                        );
                        notifySent(email, "Sent!");
                    } catch (Exception e) {
                        notifyError(email, e.getMessage());
                    }
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            smtpIndex = (smtpIndex + 1) % smtpServers.size();
        }
        executorService.shutdown();
        return null;
    }

    public void addObserver(SendMailObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SendMailObserver observer) {
        observers.remove(observer);
    }

    public void notifySent(String email, String status) {
        for (SendMailObserver observer : observers) {
            observer.onSendMailSuccess(email, status);
        }
    }

    public void notifyError(String email, String status) {
        for (SendMailObserver observer : observers) {
            observer.onSendMailFailed(email, status);
        }
    }

    @Override
    protected void process(List<String> chunks) {
        for (String chunk : chunks) {
            System.out.println(chunk);
        }
    }

    @Override
    protected void done() {

    }
}
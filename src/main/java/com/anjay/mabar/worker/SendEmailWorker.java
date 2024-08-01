package com.anjay.mabar.worker;

import com.anjay.mabar.models.EmailDetails;
import com.anjay.mabar.models.EmailList;
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
    private List<EmailList> emailList;
    private List<SendMailObserver> observers;
    private int connectionCount;
    private List<SMTPServer> smtpServers;
    private EmailDetails emailDetails;

    public SendEmailWorker(List<EmailList> emailList, List<SMTPServer> smtpServers, int connectionCount, EmailDetails emailDetails) {
        this.emailList = emailList;
        this.smtpServers = smtpServers;
        this.connectionCount = connectionCount;
        this.emailDetails = emailDetails;
        this.observers = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    protected Void doInBackground() throws Exception {
        int smtpIndex = 0;
        for (int i = 0; i < emailList.size(); i += 1) {
            SMTPServer smtpServer = smtpServers.get(smtpIndex);
            for (int j = 0; j < 1 && (i + j) < emailList.size(); j++) {
                int index = i + j;
                String email = emailList.get(index).getEmailAddress();
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
                        notifySent("Sent!", index, smtpServer.getUsername());
                    } catch (Exception e) {
                        notifyError(e.getMessage(), index, smtpServer.getUsername());
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

    public void notifySent(String status, int index, String sendBy) {
        for (SendMailObserver observer : observers) {
            observer.onSendMailSuccess(status, index, sendBy);
        }
    }

    public void notifyError(String status, int index, String sendBy) {
        for (SendMailObserver observer : observers) {
            observer.onSendMailFailed(status, index, sendBy);
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
package com.anjay.mabar.worker;

import com.anjay.mabar.models.*;
import com.anjay.mabar.observers.SendMailObserver;
import com.anjay.mabar.utils.EmailSender;
import com.anjay.mabar.utils.SimpleMail;
import org.simplejavamail.api.email.ContentTransferEncoding;
import org.simplejavamail.api.email.Email;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SendEmailWorker extends SwingWorker<Void, String> {
    private ExecutorService executorService;
    private List<EmailList> emailList;
    private List<SendMailObserver> observers;
    private int connectionCount;
    private List<SMTPServer> smtpServers;
    private List<EmailHeader> headers;
    private EmailDetails emailDetails;
    private SendConfig sendConfig;

    public SendEmailWorker(List<EmailList> emailList, List<SMTPServer> smtpServers, List<EmailHeader> headers, int connectionCount, EmailDetails emailDetails, SendConfig sendConfig) {
        this.emailList = emailList;
        this.smtpServers = smtpServers;
        this.headers = headers;
        this.connectionCount = connectionCount;
        this.sendConfig = sendConfig;
        this.emailDetails = emailDetails;
        this.observers = new ArrayList<>();

    }

    @Override
    protected Void doInBackground() throws Exception {
        int smtpIndex = 0;

        int con = Integer.parseInt(sendConfig.getConnectionCount());
        int sleep = Integer.parseInt(sendConfig.getSleepTime());
        int thread = Integer.parseInt(sendConfig.getThreadCount());
        int priority = sendConfig.getMailPriority();

        this.executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < emailList.size(); i += con) {
            SMTPServer smtpServer = smtpServers.get(smtpIndex);
            for (int j = 0; j < con && (i + j) < emailList.size(); j++) {
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
                        String messageId = emailDetails.getMessageID();
                        ContentTransferEncoding encoding = sendConfig.getContentTransferEncoding();
                        System.out.println("Thread : " + Thread.currentThread().getName());

                        SimpleMail.sendMail(
                                email,smtpServer.getUsername(),smtpServer.getPassword(), fromName, subject, body, messageId, headers, priority, encoding );

                        notifySent("Sent!", index, smtpServer.getUsername());
                    } catch (Exception e) {
                        notifyError(e.getMessage(), index, smtpServer.getUsername());
                    }
                    try {
                        Thread.sleep(sleep);
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


    public void cancel() {
        executorService.shutdownNow();
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
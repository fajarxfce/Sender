package com.anjay.mabar.worker;

import com.anjay.mabar.models.SMTPServer;
import com.anjay.mabar.models.Smtp;
import com.anjay.mabar.observers.SendMailObserver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendEmailWorker extends SwingWorker<Void, String> {
    private ExecutorService executorService;
    private List<String> emailList;
    private List<SendMailObserver> observers;
    private int connectionCount;
    private List<SMTPServer> smtpServers;

    public SendEmailWorker(List<String> emailList, List<SMTPServer> smtpServers, int connectionCount) {
        this.emailList = emailList;
        this.smtpServers = smtpServers;
        this.connectionCount = connectionCount;
        this.observers = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    protected Void doInBackground() throws Exception {

        System.out.println("Email list size: " + emailList.size());
        System.out.println("SMTP Server size: " + smtpServers.size());

        int smtpIndex = 0;
        for (int i = 0; i < emailList.size(); i += 3) {
            SMTPServer smtpServer = smtpServers.get(smtpIndex);
            for (int j = 0; j < 3 && (i + j) < emailList.size(); j++) {
                String email = emailList.get(i + j);
                executorService.submit(() -> {
                    try {
                        // Send email using smtpServer
                        System.out.println("Email sent to: " + email + " using " + smtpServer.getUsername());
//                        notifySent(email);
                    } catch (Exception e) {
                        System.out.println("Email failed to send to: " + email + " using " + smtpServer.getUsername());
//                        notifyError(email);
                    }
                    try {
                        Thread.sleep(3000);
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

    public void notifySent(String email) {
        for (SendMailObserver observer : observers) {
            observer.onSendMailSuccess(email);
        }
    }
    public void notifyError(String email) {
        for (SendMailObserver observer : observers) {
            observer.onSendMailFailed(email);
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

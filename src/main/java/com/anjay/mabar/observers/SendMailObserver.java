package com.anjay.mabar.observers;

public interface SendMailObserver {
    void onSendMailSuccess(String status, int index, String sendBy);
    void onSendMailFailed(String status, int index, String sendBy);
}

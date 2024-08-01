package com.anjay.mabar.observers;

public interface SendMailObserver {
    void onSendMailSuccess(String email, String status);
    void onSendMailFailed(String email, String status);
}

package com.anjay.mabar.observers;

public interface SendMailObserver {
    void onSendMailSuccess(String email);
    void onSendMailFailed(String message);
}

package com.anjay.mabar.observers;

public interface SendMailObserver {
    void onSendMailSuccess(String status, int index);
    void onSendMailFailed(String status, int index);
}

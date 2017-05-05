package com.example.android.tranner.categoryscreen;

/**
 * Created by Michał on 2017-05-03.
 */

public class ReloadEvent {
    private String mMessage;

    public ReloadEvent() {
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}

package com.cst2335.myapplication;

public class Message {

    private long id;
    private String message;
    private boolean isSent;
    private boolean isReceived;


    public Message(){

    }

    public Message(long id, String message, boolean isSent, boolean isReceived) {
        this.id = id;
        this.message = message;
        this.isSent = isSent;
        this.isReceived = isReceived;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }
}

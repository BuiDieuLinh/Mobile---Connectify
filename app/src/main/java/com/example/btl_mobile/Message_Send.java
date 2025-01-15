package com.example.btl_mobile;

public class Message_Send {
    private int senderID;
    private int receiverID;
    private String content;

    public Message_Send(int senderID, int receiverID, String content) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
    }

    // Getters và setters
    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

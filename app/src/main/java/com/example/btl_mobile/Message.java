package com.example.btl_mobile;

import com.google.gson.annotations.SerializedName;




public class Message {
    @SerializedName("messageID")
    private int messageID;

    @SerializedName("senderId")
    private int senderId;

    @SerializedName("receiverID")
    private int receiverID;

    @SerializedName("content")
    private String content;

    @SerializedName("sentAt")
    private String sentAt;

    @SerializedName("isRead")
    private boolean isRead;

    // Getter và Setter
    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverID;
    }

    public void setReceiverId(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}


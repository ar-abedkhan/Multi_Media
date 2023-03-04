package com.abedkhan.multimedia.Model;

public class ChatListModel {
    public String senderId,receiverId,message,chatId;
    public long timeMillis;

    public ChatListModel(String senderId, String receiverId, String message, String chatId, long timeMillis) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.chatId = chatId;
        this.timeMillis = timeMillis;
    }

    public ChatListModel() {
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
}

package com.abedkhan.multimedia.Model;

public class ChatListModel {
    public String senderId,receiverId,message,chatId;
    public long timeMilis;

    public ChatListModel(String senderId, String receiverId, String message, String chatId, long timeMilis) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.chatId = chatId;
        this.timeMilis = timeMilis;
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

    public long getTimeMilis() {
        return timeMilis;
    }

    public void setTimeMilis(long timeMilis) {
        this.timeMilis = timeMilis;
    }
}

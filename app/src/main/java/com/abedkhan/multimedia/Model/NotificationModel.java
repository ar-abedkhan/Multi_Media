package com.abedkhan.multimedia.Model;

public class NotificationModel {
    /*
    * Notification will be stored by the user
    * While an user login to an account notification section will search the latest notification!
    * */
    String postID;
    String performerID; // Here performer means who pressed the love or commented on the post
    String notificationTxt;
    long notiTimeMillis;
    boolean isClicked, isSeen;

    public NotificationModel(String postID, String performerID, String notificationTxt, long notiTimeMillis) {
        this.postID = postID;
        this.performerID = performerID;
        this.notificationTxt = notificationTxt;
        this.notiTimeMillis = notiTimeMillis;
    }

    public NotificationModel() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPerformerID() {
        return performerID;
    }

    public void setPerformerID(String performerID) {
        this.performerID = performerID;
    }

    public String getNotificationTxt() {
        return notificationTxt;
    }

    public void setNotificationTxt(String notificationTxt) {
        this.notificationTxt = notificationTxt;
    }

    public long getNotiTimeMillis() {
        return notiTimeMillis;
    }

    public void setNotiTimeMillis(long notiTimeMillis) {
        this.notiTimeMillis = notiTimeMillis;
    }
}

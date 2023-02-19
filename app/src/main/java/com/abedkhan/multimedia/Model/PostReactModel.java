package com.abedkhan.multimedia.Model;

public class PostReactModel {
    String postID; //postID will be used for the post React id
    String reactorName, reactorUserID;
    long reactTimeMillis;

    public PostReactModel(String postID, String reactorName, String reactorUserID, long reactTimeMillis) {
        this.postID = postID;
        this.reactorName = reactorName;
        this.reactorUserID = reactorUserID;
        this.reactTimeMillis = reactTimeMillis;
    }

    public PostReactModel() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getReactorName() {
        return reactorName;
    }

    public void setReactorName(String reactorName) {
        this.reactorName = reactorName;
    }

    public String getReactorUserID() {
        return reactorUserID;
    }

    public void setReactorUserID(String reactorUserID) {
        this.reactorUserID = reactorUserID;
    }

    public long getReactTimeMillis() {
        return reactTimeMillis;
    }

    public void setReactTimeMillis(long reactTimeMillis) {
        this.reactTimeMillis = reactTimeMillis;
    }
}

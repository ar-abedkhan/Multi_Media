package com.abedkhan.multimedia.Model;

public class PostReactModel {
    String postID; //postID will be used for the post React id
    String reactorUserID;
//    long reactTimeMillis;

    public PostReactModel(String postID, String reactorUserID) {
        this.postID = postID;
        this.reactorUserID = reactorUserID;
    }

    public PostReactModel() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getReactorUserID() {
        return reactorUserID;
    }

    public void setReactorUserID(String reactorUserID) {
        this.reactorUserID = reactorUserID;
    }
}

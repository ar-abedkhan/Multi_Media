package com.abedkhan.multimedia.Model;

public class PostReactModel {
    String postID; //postID will be used for the post React id
    String reactorName, reactorUserID;

    public PostReactModel(String postID, String reactorName, String reactorUserID) {
        this.postID = postID;
        this.reactorName = reactorName;
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

}

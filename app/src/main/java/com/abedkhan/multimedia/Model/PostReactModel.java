package com.abedkhan.multimedia.Model;

public class PostReactModel {
    String postID; //postID will be used for the post React id
    String reactorName, reactorUserID;
    int reactNumber, totalReact;

    public PostReactModel(String postID, String reactorName, String reactorUserID, int reactNumber, int totalReact) {
        this.postID = postID;
        this.reactorName = reactorName;
        this.reactorUserID = reactorUserID;
        this.reactNumber = reactNumber;
        this.totalReact = totalReact;
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

    public int getReactNumber() {
        return reactNumber;
    }

    public void setReactNumber(int reactNumber) {
        this.reactNumber = reactNumber;
    }

    public int getTotalReact() {
        return totalReact;
    }

    public void setTotalReact(int totalReact) {
        this.totalReact = totalReact;
    }
}

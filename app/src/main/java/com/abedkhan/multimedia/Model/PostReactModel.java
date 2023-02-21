package com.abedkhan.multimedia.Model;

public class PostReactModel {
    String postID, comment; //postID will be used for the post React id
    String reactorUserID;
    long reactTimeMillis,commentTimeMillis;


    public PostReactModel(String postID, String comment, String reactorUserID, long reactTimeMillis, long commentTimeMillis) {
        this.postID = postID;
        this.comment = comment;
        this.reactorUserID = reactorUserID;
        this.reactTimeMillis = reactTimeMillis;
        this.commentTimeMillis = commentTimeMillis;
    }

    public PostReactModel() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public long getCommentTimeMillis() {
        return commentTimeMillis;
    }

    public void setCommentTimeMillis(long commentTimeMillis) {
        this.commentTimeMillis = commentTimeMillis;
    }
}

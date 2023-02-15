package com.abedkhan.multimedia.Model;

public class PostModel {
    String postID, title, mainText, postImgUrl, draftOrPublished, ownerID;
    int postLike, postComment;
    long postTimeMillis;

    public PostModel(String postID, String title, String mainText, String postImgUrl, String draftOrPublished, String ownerID, int postLike, int postComment, long postTimeMillis) {
        this.postID = postID;
        this.title = title;
        this.mainText = mainText;
        this.postImgUrl = postImgUrl;
        this.draftOrPublished = draftOrPublished;
        this.ownerID = ownerID;
        this.postLike = postLike;
        this.postComment = postComment;
        this.postTimeMillis = postTimeMillis;
    }

    public PostModel() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getDraftOrPublished() {
        return draftOrPublished;
    }

    public void setDraftOrPublished(String draftOrPublished) {
        this.draftOrPublished = draftOrPublished;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }

    public int getPostComment() {
        return postComment;
    }

    public void setPostComment(int postComment) {
        this.postComment = postComment;
    }

    public long getPostTimeMillis() {
        return postTimeMillis;
    }

    public void setPostTimeMillis(long postTimeMillis) {
        this.postTimeMillis = postTimeMillis;
    }
}

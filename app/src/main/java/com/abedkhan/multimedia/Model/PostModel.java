package com.abedkhan.multimedia.Model;

public class PostModel {
    String postID, title, mainText, postImgUrl, draftOrPublished, ownerID;
    long postTimeMillis;

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

    public long getPostTimeMillis() {
        return postTimeMillis;
    }

    public void setPostTimeMillis(long postTimeMillis) {
        this.postTimeMillis = postTimeMillis;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}

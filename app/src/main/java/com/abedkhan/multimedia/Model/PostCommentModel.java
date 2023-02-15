package com.abedkhan.multimedia.Model;

public class PostCommentModel {
    String postID; //postID will be used for the post React id
//    commentator details
    String comName, comProfileName, comProfileImg, mainComment;
    long commentTimeMillis;

    public PostCommentModel(String postID, String comName, String comProfileName, String comProfileImg, String mainComment, long commentTimeMillis) {
        this.postID = postID;
        this.comName = comName;
        this.comProfileName = comProfileName;
        this.comProfileImg = comProfileImg;
        this.mainComment = mainComment;
        this.commentTimeMillis = commentTimeMillis;
    }

    public PostCommentModel() {
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComProfileName() {
        return comProfileName;
    }

    public void setComProfileName(String comProfileName) {
        this.comProfileName = comProfileName;
    }

    public String getComProfileImg() {
        return comProfileImg;
    }

    public void setComProfileImg(String comProfileImg) {
        this.comProfileImg = comProfileImg;
    }

    public String getMainComment() {
        return mainComment;
    }

    public void setMainComment(String mainComment) {
        this.mainComment = mainComment;
    }

    public long getCommentTimeMillis() {
        return commentTimeMillis;
    }

    public void setCommentTimeMillis(long commentTimeMillis) {
        this.commentTimeMillis = commentTimeMillis;
    }
}

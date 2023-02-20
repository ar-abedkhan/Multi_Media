package com.abedkhan.multimedia.Model;

public class PostCommentModel {
    String postID; //postID will be used for the post React id
//    commentator details
    String commenterName, commenterImg, mainComment;
    long commentTimeMillis;

    public PostCommentModel(String postID, String comName, String comProfileImg, String mainComment, long commentTimeMillis) {
        this.postID = postID;
        this.commenterName = comName;
        this.commenterImg = comProfileImg;
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

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterImg() {
        return commenterImg;
    }

    public void setCommenterImg(String commenterImg) {
        this.commenterImg = commenterImg;
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

package com.abedkhan.multimedia.Model;

public class FollowerFollowingModel {
//    this same class will be used for the follower and following
    String OwnProfileID;
    String followerID;
    String followProfileImg, followerName;

    public FollowerFollowingModel(String ownProfileID, String followerID, String followProfileImg, String followerName) {
        OwnProfileID = ownProfileID;
        this.followerID = followerID;
        this.followProfileImg = followProfileImg;
        this.followerName = followerName;
    }

    public FollowerFollowingModel() {
    }

    public String getOwnProfileID() {
        return OwnProfileID;
    }

    public void setOwnProfileID(String ownProfileID) {
        OwnProfileID = ownProfileID;
    }

    public String getFollowerID() {
        return followerID;
    }

    public void setFollowerID(String followerID) {
        this.followerID = followerID;
    }

    public String getFollowProfileImg() {
        return followProfileImg;
    }

    public void setFollowProfileImg(String followProfileImg) {
        this.followProfileImg = followProfileImg;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }
}

package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView profileName, postingTime, postTitle,mainStory, readMore;
    public TextView reactCount, commentCount, followingOption;
    public ImageView profileImg;
    public ImageView postImg;
    public LinearLayout shareOption;
    public PostViewHolder(@NonNull View view) {
        super(view);
        profileName  = view.findViewById(R.id.UserProfileName);
        postingTime  = view.findViewById(R.id.postTime);
        postTitle  = view.findViewById(R.id.postStoryTitle);
        mainStory  = view.findViewById(R.id.main_story);
        readMore  = view.findViewById(R.id.more);
        reactCount  = view.findViewById(R.id.postReactCount);
        commentCount  = view.findViewById(R.id.postCommentCount);
        followingOption  = view.findViewById(R.id.followTheWriter);
        postImg  = view.findViewById(R.id.postImg);
        profileImg  = view.findViewById(R.id.profileImg);
        shareOption  = view.findViewById(R.id.shareOption);
    }
}

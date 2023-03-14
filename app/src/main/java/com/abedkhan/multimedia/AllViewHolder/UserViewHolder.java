package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView userName,userProfession,follow;
    public CardView followCard,followingCrad;
    public CircleImageView userProfileImg;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        userProfileImg=itemView.findViewById(R.id.profileImg);
        followCard=itemView.findViewById(R.id.followTheWriter);
        followingCrad=itemView.findViewById(R.id.youAreFollowingTheWriter);
        userProfession=itemView.findViewById(R.id.userProfession);
        userName=itemView.findViewById(R.id.profilename);
        follow=itemView.findViewById(R.id.followText);


    }
}

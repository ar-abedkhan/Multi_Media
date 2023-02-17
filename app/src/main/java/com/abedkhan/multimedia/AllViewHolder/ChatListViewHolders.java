package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListViewHolders extends RecyclerView.ViewHolder {
    public TextView name,message,lastSeen;
    public CircleImageView profileImg;

    public ChatListViewHolders(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.chatUsernameTV);
        message=itemView.findViewById(R.id.lastMessageTV);
        lastSeen=itemView.findViewById(R.id.chattimeTv);
        profileImg=itemView.findViewById(R.id.chatUserimgTV);


    }
}

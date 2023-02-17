package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;


public class ChatViewholder extends RecyclerView.ViewHolder {
    public TextView message,messtime;
    public ImageView image;

    public ChatViewholder(@NonNull View itemView) {
        super(itemView);

            message=itemView.findViewById(R.id.messTv);
            messtime=itemView.findViewById(R.id.timeTv);


    }
}

package com.abedkhan.multimedia.AllViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout notiContainer;
    public TextView notiMainTxt, notiTimeMillis;
    public ImageView profileImg;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        notiContainer = itemView.findViewById(R.id.notificationContainer);
        profileImg = itemView.findViewById(R.id.profileImg);
        notiMainTxt = itemView.findViewById(R.id.notiMainTxt);
        notiTimeMillis = itemView.findViewById(R.id.notiTimeMillis);
    }
}

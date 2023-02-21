package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView img;
    public TextView name,comment,time;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.comment_profileName);
        img=itemView.findViewById(R.id.comment_ProfileImg);
        time=itemView.findViewById(R.id.commentTime);
        comment=itemView.findViewById(R.id.main_comment);


    }
}

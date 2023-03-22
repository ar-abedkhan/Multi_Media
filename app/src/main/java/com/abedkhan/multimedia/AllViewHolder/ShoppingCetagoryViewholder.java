package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

public class ShoppingCetagoryViewholder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView name;


    public ShoppingCetagoryViewholder(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.cetagory_Name);
        img =itemView.findViewById(R.id.cetagory_Img);


    }
}

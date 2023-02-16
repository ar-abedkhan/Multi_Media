package com.abedkhan.multimedia.AllViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryName;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.categoryName);
    }
}

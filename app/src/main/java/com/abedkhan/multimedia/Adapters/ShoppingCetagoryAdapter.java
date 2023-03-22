package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.ShoppingCetagoryViewholder;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.ProductModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingCetagoryAdapter extends RecyclerView.Adapter<ShoppingCetagoryViewholder> {
    Context context;
    List<ProductModel>cetagories;

    public ShoppingCetagoryAdapter(Context context, List<ProductModel> cetagories) {
        this.context = context;
        this.cetagories = cetagories;
    }

    @NonNull
    @Override
    public ShoppingCetagoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cetegory_design,parent,false);

        return new ShoppingCetagoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCetagoryViewholder holder, int position) {

        ProductModel productModel=cetagories.get(position);

        holder.name.setText(productModel.getCetagoryName());


        Glide.with(context).load(productModel.getCetegoryImg()).into(holder.img);







    }

    @Override
    public int getItemCount() {
        return cetagories.size();
    }
}

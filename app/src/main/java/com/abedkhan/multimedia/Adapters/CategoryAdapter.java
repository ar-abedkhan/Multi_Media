package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.CategoryViewHolder;
import com.abedkhan.multimedia.Listeners.CategoryListener;
import com.abedkhan.multimedia.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    List<String> categoryList;
    CategoryListener listener;

    public CategoryAdapter(Context context, List<String> categoryList, CategoryListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryName.setText(categoryList.get(position));

        holder.categoryName.setOnClickListener(view -> {
//            Log.i("TAG", "In the Category Adapter: "+categoryList.get(position));
            listener.selectedCategory(categoryList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

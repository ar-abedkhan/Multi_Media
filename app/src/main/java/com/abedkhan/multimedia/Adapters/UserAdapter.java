package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.PostViewHolder;
import com.abedkhan.multimedia.AllViewHolder.UserViewHolder;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    Context context;
    List<UserModel> userModelList;

    public UserAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_recycler_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        UserModel userModel=userModelList.get(position);


        Glide.with(context).load(userModel.getProfileImgUrl()).placeholder(R.drawable.ic_baseline_person_24).into(holder.userProfileImg);
//            Glide.with(context).load(userModel.getUser_profile()).into(holder.userprofileImg);

        holder.userName.setText(userModel.getUserName());
        holder.userProfession.setText(userModel.getProfession());

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }
}

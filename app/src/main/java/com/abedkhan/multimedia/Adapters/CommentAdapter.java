package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.CommentViewHolder;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Model.PostCommentModel;
import com.abedkhan.multimedia.Model.PostReactModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    List<PostCommentModel> postCommentModelList;
    Context context;
    DatabaseReference databaseReference;

    String ownerFullName,ownerProfileImg;

    public CommentAdapter(List<PostCommentModel> postCommentModelList, Context context) {
        this.postCommentModelList = postCommentModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_design,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        PostCommentModel postCommentModel=postCommentModelList.get(position);


        holder.comment.setText(postCommentModel.getMainComment());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");  //     MMM dd,yyyy
        Date date = new Date(postCommentModel.getCommentTimeMillis());
        holder.time.setText(simpleDateFormat.format(date));



        databaseReference= FirebaseDatabase.getInstance().getReference();

//        databaseReference.child("User").child(postCommentModel.getPostID()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<UserModel> userModelList= new ArrayList<>();
//
//                UserModel userModel=snapshot.getValue(UserModel.class);
//                userModelList.add(userModel);
//
////                if (!userModel.equals("")){
////                    ownerFullName = userModelList.get(0).getFullName();
////                    ownerProfileImg = userModelList.get(0).getProfileImgUrl();
//
//                    holder.name.setText(userModel.getFullName());
//                    Glide.with(context).load(ownerProfileImg).placeholder(R.drawable.ic_baseline_person_24).into(holder.img);
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        databaseReference.child("User").child(postCommentModel.getPostID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);

//                ownerFullName = model.getFullName();
//                ownerProfileImg = model.getProfileImgUrl();

                holder.name.setText(postCommentModel.getCommenterName());
                Glide.with(context).load(postCommentModel.getCommenterImg()).placeholder(R.drawable.ic_baseline_person_24).into(holder.img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        holder.img.setOnClickListener(view -> {
//
//
//            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
//            ProfileFragment profileFragment = new ProfileFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("VisitedUserID", postCommentModel.getCommenterName());
//            profileFragment.setArguments(bundle);
//
//            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, profileFragment).addToBackStack(null).commit();
//
//
//        });
//





    }

    @Override
    public int getItemCount() {
        return postCommentModelList.size();
    }
}

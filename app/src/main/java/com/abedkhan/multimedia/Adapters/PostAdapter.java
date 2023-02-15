package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.PostViewHolder;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    Context context;
    List<PostModel> postList;
    DatabaseReference databaseReference;

    public PostAdapter(Context context, List<PostModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.post_recycler_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostModel model = postList.get(position);

        holder.postTitle.setText(model.getTitle());
        holder.mainStory.setText(model.getMainText());

        if (!model.getPostImgUrl().equals("")){
            Glide.with(context).load(model.getPostImgUrl()).placeholder(R.drawable.lightning_tree).into(holder.postImg);
        }
        else {
            holder.postImg.setVisibility(View.GONE);
        }

//        --Setting post time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(model.getPostTimeMillis());
//        holder.postingTime.setText(simpleDateFormat.format("MMM dd,yyyy HH:mm"));

//        #Getting post Owner id and owner Profile Image
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("User").child(model.getOwnerID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UserModel> userModelList= new ArrayList<>();
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
//                    UserModel userModel = (UserModel) dataSnapshot.getValue(UserModel.class);
//                    userModelList.add(userModel);
//
//                }
                UserModel userModel = snapshot.getValue(UserModel.class);
                userModelList.add(userModel);

                if (!userModelList.isEmpty()){
                    holder.profileName.setText(userModelList.get(0).getFullName());
                    Glide.with(context).load(userModelList.get(0).getProfileImgUrl()).placeholder(R.drawable.lightning_tree).into(holder.profileImg);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "Error :-> "+ error.toException().getLocalizedMessage());

            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

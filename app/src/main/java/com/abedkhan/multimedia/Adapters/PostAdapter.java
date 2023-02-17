package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.PostViewHolder;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.ReadStoryFragment;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostModel;
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
import java.util.Collections;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    Context context;
    List<PostModel> postList;
    DatabaseReference databaseReference;
    PostListener listener;

    public PostAdapter(Context context, List<PostModel> postList, PostListener listener) {
        this.context = context;
        this.postList = postList;
        this.listener = listener;
        Collections.reverse(postList);
    }

//    public PostAdapter(Context context, List<PostModel> postList) {
//        this.context = context;
//        this.postList = postList;
//        Collections.reverse(postList);
//    }


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
        holder.reactCount.setText(""+model.getPostLike());
        holder.commentCount.setText(String.valueOf(model.getPostComment()));


        if (!model.getPostImgUrl().equals("")){
            Glide.with(context).load(model.getPostImgUrl()).placeholder(R.drawable.lightning_tree).into(holder.postImg);
        }
        else {
            holder.postImg.setVisibility(View.GONE);
        }

//        --Setting post time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(model.getPostTimeMillis());
        holder.postingTime.setText(simpleDateFormat.format(date));


//        #Getting post Owner id and owner Profile Image
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("User").child(model.getOwnerID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UserModel> userModelList= new ArrayList<>();

                UserModel userModel = snapshot.getValue(UserModel.class);
                userModelList.add(userModel);

                if (!userModelList.isEmpty()){
                    holder.profileName.setText(userModelList.get(0).getFullName());
                    Glide.with(context).load(userModelList.get(0).getProfileImgUrl()).placeholder(R.drawable.lightning_tree).into(holder.profileImg);

                }

//                Handling in post click
                holder.mainStory.setOnClickListener(view -> {

                    AppCompatActivity appCompatActivity= (AppCompatActivity) view.getContext();
                    ReadStoryFragment readStoryFragment=new ReadStoryFragment();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,readStoryFragment).addToBackStack(null).commit();



                });

holder.readMore.setOnClickListener(view -> {

//    AppCompatActivity appCompatActivity= (AppCompatActivity) view.getContext();
//    ReadStoryFragment readStoryFragment=new ReadStoryFragment();
//    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,readStoryFragment).addToBackStack(null).commit();
    listener.gotoFragmentWithValue(new ReadStoryFragment(), model.getOwnerID());

});

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "Error :-> "+ error.toException().getLocalizedMessage());

            }
        });

//        #Hanling Profile image clicked
        holder.profileImg.setOnClickListener(view -> {
//          parse data to profile adapter
            listener.gotoFragmentWithValue(new ProfileFragment(), model.getOwnerID());
        });

//        #Hanling Profile name clicked
        holder.profileName.setOnClickListener(view -> {
//           parse data to profile adapter
            listener.gotoFragmentWithValue(new ProfileFragment(), model.getOwnerID());
        });

//        Handling follow option clicked event
        holder.followingOption.setOnClickListener(view -> {
            boolean isFollowing = listener.followButtonClickedEvent(model.getOwnerID());
            if (isFollowing){
                holder.followingOption.setText("Following");
            }
        });




    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

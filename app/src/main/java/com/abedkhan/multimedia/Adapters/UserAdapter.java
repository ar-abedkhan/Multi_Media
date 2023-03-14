package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.UserViewHolder;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.ReadStoryFragment;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.FollowerFollowingModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
    Context context;
    List<UserModel> userModelList;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    String currentUserID;
    String visitedUserID;
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

        UserModel userModel = userModelList.get(position);


        Glide.with(context).load(userModel.getProfileImgUrl()).placeholder(R.drawable.ic_baseline_person_24).into(holder.userProfileImg);
        holder.userName.setText(userModel.getUserName());
        holder.userProfession.setText(userModel.getProfession());


        //pass data from profile.............
        holder.itemView.setOnClickListener(view -> {
            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
//            passing post data to the fragment

            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString("VisitedUserID", userModel.getUserID());
            profileFragment.setArguments(bundle);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, profileFragment).addToBackStack(null).commit();


        });


////        Checking if the current user following the visited ID
//        try {
//            databaseReference.child("Following").child(currentUserID).child(visitedUserID).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        holder.follow.setText("Following");
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        } catch (Exception e){
//        }
//        String visitedUserProfileImg = null, visitedUserName = null;
//
////        Handling follow button clicked
////        TODO: unfollow
//        holder.followCard.setOnClickListener(view -> {
//            boolean isFollowing = isFollowing(visitedUserID);
//            if(!currentUserID.equals(visitedUserID) && !isFollowing){
//                Map<String, Object> map = new HashMap<>();
//                map.put("OwnProfileID", currentUserID);
//                map.put("followerID", visitedUserID);
//                map.put("followerName", visitedUserName);
//                map.put("followProfileImg", visitedUserProfileImg);
//
////                Including the visited user to the following list
//                databaseReference.child("Following").child(currentUserID).child(visitedUserID).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        holder.follow.setText("Following");
//
//                        String currentUserName = null, currentUserImg = null;
//
//                        /*
//                         * This process will be completed in two steps
//                         * 1. Firstly will check if the visiting user following the current user or not
//                         * 2. If the visitor is following the user then isFollowing method will return false and the whole process will be cancelled else the process will continue
//                         * 3. Lastly the user will be stored in the followed user's followers table
//                         * */
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("OwnProfileID", visitedUserID);
//                        map.put("followerID", currentUserID);
//                        map.put("followerName", currentUserName);
//                        map.put("followProfileImg", currentUserImg);
//                        databaseReference.child("Followers").child(visitedUserID).child(currentUserID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    try {
//                                        Toast.makeText(context, "Following the user", Toast.LENGTH_SHORT).show();
//
//                                    }catch (Exception e){
//
//                                    }
//                                }else{
//                                    Log.i("TAG", "Follower upload failed: "+ task.getException().getLocalizedMessage());
//                                }
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                    }
//                });
//            }
//        });
//
//
//    }
//
//    boolean isFollowing = false;
//    private boolean isFollowing(String visitedUserID) {
//        databaseReference.child("Following").child(currentUserID).child(visitedUserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try {
//                    for (DataSnapshot snap: snapshot.getChildren()) {
//                        FollowerFollowingModel model = snap.getValue(FollowerFollowingModel.class);
//                        if (visitedUserID.equals(model.getFollowerID())){
//                            isFollowing = true;
//                        }
//                    }
//                }catch (Exception e){
//                    isFollowing = false;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                isFollowing = false;
//
//            }
//        });
//        return isFollowing;
//    }
//
//


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }
}
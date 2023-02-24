package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Model.FollowerFollowingModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentProfileBinding;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    private FragmentProfileBinding binding;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    List<UserModel>userModelList;
    String currentUserID, currentUserName, currentUserImg,postID;
    String visitedUserID, visitedUserProfileImg, visitedUserName;
    int publishedPost,savePost,followers,following;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(getLayoutInflater(),container,false);

        //        user profile clicked
        floatingButtonClicked();

//        binding.settings.setOnClickListener(view -> {
//            Intent intent = new Intent(requireContext(), ContainerActivity.class);
//            intent.putExtra("currentUser", currentUser);
//            startActivity(intent);
//        });

//        Log.i("TAG", "Profile fragment 1 ");

        databaseReference=FirebaseDatabase.getInstance().getReference();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseUser!=null){
            currentUserID =firebaseUser.getUid();
        }













//        ##Getting current user data
        databaseReference.child("User").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.i("TAG", "Profile fragment 2 ");

                UserModel userModel=snapshot.getValue(UserModel.class);

                if (userModel!=null){
                    currentUserName = userModel.getFullName();
                    currentUserImg = userModel.getProfileImgUrl();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        #Checking if the request is coming from the Profile fragment or any other fragment and if there are any arguments
        try {


            visitedUserID  = getArguments().getString("VisitedUserID");
//            Log.i("TAG", "Profile fragment -- "+visitedUserID);
            databaseReference.child("User").child(visitedUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    UserModel userModel=snapshot.getValue(UserModel.class);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                    Date date = new Date(userModel.getIdCreationTimeMillis());


                    if (!userModel.getUserID().equals(currentUserID)){
                        binding.logeOutBtn.setVisibility(View.GONE);
                        binding.settings.setVisibility(View.GONE);
                    }


                    if (userModel!=null){
                        binding.userProfileName.setText(userModel.getFullName().trim());
                        binding.userJoinedDate.setText(simpleDateFormat.format(date));
                        binding.userProfession.setText(userModel.getProfession().trim());
                        binding.userCountry.setText(userModel.getLivingCountry().trim());
                        binding.userLiveIn.setText(userModel.getLivingCity().trim());
                        binding.userGender.setText(userModel.getGender().trim());
                        binding.userName.setText(userModel.getUserName().trim());
                        binding.userMail.setText(userModel.getEmail().trim());

                        if (userModel.getUserBio().isEmpty()){
                            binding.userProfileBio.setVisibility(View.GONE);
                        }else {
                            binding.userProfileBio.setText(userModel.getUserBio().trim());
                        }
                        binding.userDateofBirth.setText(userModel.getDateOfBirth().trim());

                        Glide.with(getActivity()).load(userModel.getProfileImgUrl()).placeholder(R.drawable.lightning_tree).into(binding.userProfileImg);

                        Log.i("tag", "onCreate: "+userModel.getFullName());
                        Log.i("tag", "onCreate: "+userModel.getUserID());

//                        visitedUserID = userModel.getUserID();
                        visitedUserName = userModel.getUserName();
                        visitedUserProfileImg = userModel.getProfileImgUrl();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "User visiting failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
//        *setting the current user data if getting argument fails





            databaseReference.child("User").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    UserModel userModel=snapshot.getValue(UserModel.class);


                    if (!userModel.getUserID().equals(currentUserID)){
                        binding.logeOutBtn.setVisibility(View.GONE);
                        binding.settings.setVisibility(View.GONE);
                    }




                    if (userModel!=null){

                        binding.userProfileName.setText(userModel.getFullName().trim());
//                  binding.userJoinedDate.setText((int) userModel.getIdCreationTimeMillis());
                        binding.userProfession.setText(userModel.getProfession().trim());
                        binding.userCountry.setText(userModel.getLivingCountry().trim());
                        binding.userLiveIn.setText(userModel.getLivingCity().trim());
                        binding.userGender.setText(userModel.getGender().trim());
                        binding.userName.setText(userModel.getUserName().trim());
                        binding.userMail.setText(userModel.getEmail().trim());

                        if (userModel.getUserBio().isEmpty()){
                            binding.userProfileBio.setVisibility(View.GONE);
                        }else {
                            binding.userProfileBio.setText(userModel.getUserBio().trim());
                        }
                        binding.userDateofBirth.setText(userModel.getDateOfBirth().trim());

                        Glide.with(getActivity()).load(userModel.getProfileImgUrl()).placeholder(R.drawable.lightning_tree).into(binding.userProfileImg);

                        Log.i("tag", "onCreate: "+userModel.getFullName());
                        Log.i("tag", "onCreate: "+userModel.getUserID());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //        Hiding follow button for own profile
                binding.followOptionContainer.setVisibility(View.GONE);
//
        }


        //handeling post count....
//        postID = databaseReference.push().getKey();
//
//        databaseReference.child("User").child("Post").child(postID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                TODO: get total length
////                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
//                List<UserModel>postModelList=new ArrayList<>();
//
////                List<String> postSize = new ArrayList<>();
//
//                for (DataSnapshot snap: snapshot.getChildren()) {
//                    String userId = snap.getKey();
//                    postModelList.add(userId);
//                }
//
//                try {
//                    publishedPost = postModelList.size();
//                    binding.publishPostBtn.setText(publishedPost);
//                }catch (Exception e){
//                    binding.publishPostBtn.setText("0");
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//












//        Handling follow button clicked
//        TODO: unfollow
        binding.followOptionContainer.setOnClickListener(view -> {
            boolean isFollowing = isFollowing(visitedUserID);
            if(!currentUserID.equals(visitedUserID) && !isFollowing){
                Map<String, Object> map = new HashMap<>();
                map.put("OwnProfileID", currentUserID);
                map.put("followerID", visitedUserID);
                map.put("followerName", visitedUserName);
                map.put("followProfileImg", visitedUserProfileImg);

//                Including the visited user to the following list
                databaseReference.child("Following").child(currentUserID).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.followText.setText("following");

                        /*
                        * This process will be completed in two steps
                        * 1. Firstly will check if the visiting user following the current user or not
                        * 2. If the visitor is following the user then isFollowing method will return false and the whole process will be cancelled else the process will continue
                        * 3. Lastly the user will be stored in the followed user's followers table
                        * */
                        Map<String, Object> map = new HashMap<>();
                        map.put("OwnProfileID", visitedUserID);
                        map.put("followerID", currentUserID);
                        map.put("followerName", currentUserName);
                        map.put("followProfileImg", currentUserImg);
                        databaseReference.child("Followers").child(visitedUserID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(), "Following the user", Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.i("TAG", "Follower upload failed: "+ task.getException().getLocalizedMessage());
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

        });


        return binding.getRoot();
    }

    boolean isFollowing = false;
    private boolean isFollowing(String visitedUserID) {
        databaseReference.child("Following").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        FollowerFollowingModel model = snap.getValue(FollowerFollowingModel.class);
                        if (visitedUserID.equals(model.getFollowerID())){
                            isFollowing = true;
                        }
                    }
                }catch (Exception e){
                    isFollowing = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isFollowing = false;

            }
        });
        return isFollowing;
    }

    private void floatingButtonClicked() {

        binding.settings.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("settingsClicked", true);
            intent.putExtra("currentUser", currentUserID);
            startActivity(intent);
        });

        binding.message.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("isMessageClicked", true);
            startActivity(intent);
        });

        binding.addPost.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.putExtra("isAddClicked", true);
            startActivity(intent);
        });

        binding.logeOutBtn.setOnClickListener(view -> {
            firebaseAuth.signOut();
            startActivity(new Intent(getContext(),ContainerActivity.class));

        });

    }
}
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
import com.abedkhan.multimedia.Listeners.PostListener;
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

public class ProfileFragment extends Fragment{
    public ProfileFragment() {
    }

    private FragmentProfileBinding binding;
    DatabaseReference databaseReference, userStatusReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Intent intent;
    List<UserModel>userModelList;
    String currentUserID, currentUserName, currentUserImg,postID;
    String requestedIdForPost; // This variable contains currently visited profile ID
    String visitedUserID;
    String visitedUserProfileImg, visitedUserName;
    int publishedPostCount,savePostCount,followersCount,followingCount;
//    PostListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(getLayoutInflater(),container,false);

        //        user profile clicked
        floatingButtonClicked();
        intent=getActivity().getIntent();

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

//try {
//    visitedUserID=intent.getStringExtra("VisitedUserID");
//
//}catch (Exception e){
//
//}
            visitedUserID  = getArguments().getString("VisitedUserID");
            requestedIdForPost = visitedUserID;
//            Log.i("TAG", "Profile fragment -- "+visitedUserID);
            databaseReference.child("User").child(visitedUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

               UserModel userModel=snapshot.getValue(UserModel.class);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
                    Date date = new Date(userModel.getIdCreationTimeMillis());


                   // setting profile data to others user........
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

                        }else{
                            binding.userProfileBio.setText(userModel.getUserBio().trim());
                        }
                        binding.userDateofBirth.setText(userModel.getDateOfBirth().trim());

                        try {

                            Glide.with(getContext()).load(userModel.getProfileImgUrl())
                                    .placeholder(R.drawable.lightning_tree).into(binding.userProfileImg);

                        }catch (Exception exception){

                        }

                        Log.i("tag", "onCreate: "+userModel.getFullName());
                        Log.i("tag", "onCreate: "+userModel.getUserID());

                        visitedUserID = userModel.getUserID();
                        visitedUserName = userModel.getUserName();
                        visitedUserProfileImg = userModel.getProfileImgUrl();

                    }

//others all setup to others user profile here.......................................................................................

                    //setting following to others user profile............
                    databaseReference.child("Following").child(visitedUserID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {


//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
                                List<String> postSize = new ArrayList<>();
                                for (DataSnapshot snap: snapshot.getChildren()) {
                                    String userId = snap.getKey();
                                    postSize.add(userId);

                                }

                                try {
                                    followingCount = postSize.size();
                                    binding.iAmFollowingBtn.setText(postSize.size()+"");
                                }catch (Exception e){
                                    binding.iAmFollowingBtn.setText("0");
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    //setting followers to others user profile............
                    databaseReference.child("Followers").child(visitedUserID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {


//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
                                List<String> postSize = new ArrayList<>();
                                for (DataSnapshot snap: snapshot.getChildren()) {
                                    String userId = snap.getKey();
                                    postSize.add(userId);

                                }

                                try {
                                    followingCount = postSize.size();
                                    binding.myFollowersbtn.setText(postSize.size()+"");
                                }catch (Exception e){
                                    binding.myFollowersbtn.setText("0");
                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    //hiode logeout and settings butto from others user..............
                    if (!userModel.getUserID().equals(currentUserID)){
                        binding.logeOutBtn.setVisibility(View.INVISIBLE);
                        binding.settings.setVisibility(View.INVISIBLE);
                        binding.message.setVisibility(View.VISIBLE);
                        binding.addPost.setVisibility(View.VISIBLE);
                    }




                     //setting published post count..................................................................
                        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                List<String> postSize = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    PostModel postModel = dataSnapshot.getValue(PostModel.class);
                                    if (postModel.getOwnerID().equals(visitedUserID)) {

                                        String userId = dataSnapshot.getKey();
                                        postSize.add(userId);

//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
//                                        List<String> postSize = new ArrayList<>();
//                                        for (DataSnapshot snap : snapshot.getChildren()) {
//                                            String userId = snap.getKey();
//                                            postSize.add(userId);
//
//                                        }
                                    }

//                                    try {
//                                        publishedPostCount = postSize.size();
//                                        Log.i("tt", "size: "+ postSize.size());
//                                        binding.publishPostBtn.setText(postSize.size() + "");
//                                    } catch (Exception e) {
//                                        binding.publishPostBtn.setText("0");
//                                    }

                                }
                                try {
                                    publishedPostCount = postSize.size();
                                    Log.i("tt", "size: "+ postSize.size());
                                    binding.publishPostBtn.setText(postSize.size() + "");
                                } catch (Exception e) {
                                    binding.publishPostBtn.setText("0");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "User visiting failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
//        *setting the current user data if getting argument fails..........................................

            requestedIdForPost = currentUserID;
            databaseReference.child("User").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    UserModel userModel=snapshot.getValue(UserModel.class);


                    if (userModel!=null){

                        binding.userProfileName.setText(userModel.getFullName().trim());
//                  binding.userJoinedDate.setText((int) userModel.getIdCreationTimeMillis());
                        binding.userGender.setText(userModel.getGender().trim());
                        binding.userName.setText(userModel.getUserName().trim());
                        binding.userMail.setText(userModel.getEmail().trim());

                        if (userModel.getUserBio().isEmpty()) {
                            binding.userProfileBio.setVisibility(View.GONE);
                        } else {
                            binding.userProfileBio.setText(userModel.getUserBio().trim());
                        }

                        if (userModel.getProfession().isEmpty()){
                            binding.userProfession.setVisibility(View.GONE);
                        }
                        else {
                            binding.userProfession.setText(userModel.getProfession().trim());
                        }
                        if (userModel.getLivingCountry().isEmpty()){
                            binding.userCountry.setVisibility(View.GONE);
                        }else {
                            binding.userCountry.setText(userModel.getLivingCountry().trim());
                        }

                        if (userModel.getLivingCity().isEmpty()){
                            binding.userLiveIn.setVisibility(View.GONE);
                        }else {
                            binding.userLiveIn.setText(userModel.getLivingCity().trim());
                        }

                        binding.userDateofBirth.setText(userModel.getDateOfBirth().trim());

                        try {

                            Glide.with(getContext()).load(userModel.getProfileImgUrl())
                                    .placeholder(R.drawable.lightning_tree).into(binding.userProfileImg);

                        }catch (Exception exception){

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //        Hiding follow button for own profile
                binding.followTheWriter.setVisibility(View.GONE);
                    binding.youAreFollowingTheWriter.setVisibility(View.GONE);


                    //visible menu buttons.........
            binding.logeOutBtn.setVisibility(View.VISIBLE);
            binding.settings.setVisibility(View.VISIBLE);
            binding.message.setVisibility(View.VISIBLE);
            binding.addPost.setVisibility(View.VISIBLE);

            //for current user on message option clicked intent to mess list...........
            binding.message.setOnClickListener(view -> {
                Intent intent = new Intent(requireContext(), ContainerActivity.class);
                intent.putExtra("isMessage", true);
                intent.putExtra("visitor",currentUserID);
                Log.i("visitorID", "onCreateView: "+intent);

                startActivity(intent);

            });

            // setting following to current user profile..............
            databaseReference.child("Following").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {


//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
                        List<String> postSize = new ArrayList<>();
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            String userId = snap.getKey();
                            postSize.add(userId);

                        }

                        try {
                            followingCount = postSize.size();
                            binding.iAmFollowingBtn.setText(postSize.size()+"");
                        }catch (Exception e){
                            binding.iAmFollowingBtn.setText("0");
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });






            //setting published post count..................................................................
            databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    List<String> postSize = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PostModel postModel = dataSnapshot.getValue(PostModel.class);
                        if (postModel.getOwnerID().equals(currentUserID)) {

                            String userId = dataSnapshot.getKey();
                            postSize.add(userId);

//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
//                                        List<String> postSize = new ArrayList<>();
//                                        for (DataSnapshot snap : snapshot.getChildren()) {
//                                            String userId = snap.getKey();
//                                            postSize.add(userId);
//
//                                        }
                        }

//                                    try {
//                                        publishedPostCount = postSize.size();
//                                        Log.i("tt", "size: "+ postSize.size());
//                                        binding.publishPostBtn.setText(postSize.size() + "");
//                                    } catch (Exception e) {
//                                        binding.publishPostBtn.setText("0");
//                                    }

                    }
                    try {
                        publishedPostCount = postSize.size();
                        Log.i("tt", "size: "+ postSize.size());
                        binding.publishPostBtn.setText(postSize.size() + "");
                    } catch (Exception e) {
                        binding.publishPostBtn.setText("0");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





            //setting followers to current user profile............
            databaseReference.child("Followers").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {


//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
                        List<String> postSize = new ArrayList<>();
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            String userId = snap.getKey();
                            postSize.add(userId);

                        }

                        try {
                            followingCount = postSize.size();
                            binding.myFollowersbtn.setText(postSize.size()+"");
                        }catch (Exception e){
                            binding.myFollowersbtn.setText("0");
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }





        binding.myFollowersList.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("follower", true);
            intent.putExtra("requestedIdForPost", visitedUserID);
            startActivity(intent);
        });



        binding.myFollowingList.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("following", true);
            intent.putExtra("requestedIdForPost", visitedUserID);
            startActivity(intent);
        });


        binding.myuplodedPostList.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("postlist", true);
//            intent.putExtra("requestedIdForPost", requestedIdForPost);
            intent.putExtra("requestedIdForPost", visitedUserID);
            startActivity(intent);
        });
















//        Checking if the current user following the visited ID
        try {
            databaseReference.child("Following").child(currentUserID).child(visitedUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        binding.followText.setText("Following");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e){
        }

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
                databaseReference.child("Following").child(currentUserID).child(visitedUserID).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.followText.setText("Following");

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
                        databaseReference.child("Followers").child(visitedUserID).child(currentUserID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    try {
                                        Toast.makeText(getActivity(), "Following the user", Toast.LENGTH_SHORT).show();

                                    }catch (Exception e){

                                    }
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
        databaseReference.child("Following").child(currentUserID).child(visitedUserID).addValueEventListener(new ValueEventListener() {
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
            intent.putExtra("visitor",visitedUserID);
//            Log.i("visitorID", "onCreateView: "+intent);
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
    private void status(String status){
        userStatusReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        HashMap<String , Object> hashMap=new HashMap<>();
        hashMap.put("status",status);
        userStatusReference.updateChildren(hashMap);


    }

    @Override
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }

}
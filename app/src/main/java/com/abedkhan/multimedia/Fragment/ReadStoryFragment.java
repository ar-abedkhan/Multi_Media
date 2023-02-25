package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.NonUiContext;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Adapters.CommentAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostCommentModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.PostReactModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentReadStoryBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class ReadStoryFragment extends Fragment implements PostListener {
    public ReadStoryFragment() {
    }
    FragmentReadStoryBinding binding;
    String postId, currentUser, currentUserName, currentProfileImg;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    List<PostCommentModel> commentList;
    int totalLikes=0,comment=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReadStoryBinding.inflate(getLayoutInflater(),container,false);

//        binding.readStoryFragment.setSystemUiVisibility(
//                                                    View.SYSTEM_UI_FLAG_FULLSCREEN
//                                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                                           | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        );


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        currentUser = firebaseUser.getUid();
        try {
            postId = getArguments().getString("postID");
//            currentUserName = getArguments().getString("ownerFullName");
//            currentProfileImg  = getArguments().getString("ownerProfileImg");
        }catch (Exception e){
            Log.i("TAG", "Read Fragment Error: "+e);
        }
        commentList=new ArrayList<>();



//        getting current user data
        databaseReference.child("User").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);

                currentUserName = model.getFullName();
                currentProfileImg = model.getProfileImgUrl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        getting post data from the firebase
        databaseReference.child("Post").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostModel model = snapshot.getValue(PostModel.class);


                //setting post owner name profile
databaseReference.child("User").child(model.getOwnerID()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        UserModel userModel=snapshot.getValue(UserModel.class);

        binding.writerName.setText(userModel.getFullName());
        binding.profession.setText(userModel.getProfession());
        if (!userModel.getProfileImgUrl().equals("")) {
            Glide.with(getActivity()).load(userModel.getProfileImgUrl()).placeholder(R.drawable.ic_baseline_person_24).into(binding.writerProfileImg);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                Date date = new Date(model.getPostTimeMillis());

//                setting values in the view
                binding.mainStory.setText(model.getMainText());
                binding.storyTitle.setTitle(model.getTitle());
                binding.postReactCount.setText(""+model.getPostLike());
                binding.postTime.setText(simpleDateFormat.format(date));
                if (!model.getPostImgUrl().equals("")) {
                    Glide.with(getActivity()).load(model.getPostImgUrl()).placeholder(R.drawable.lightning_tree).into(binding.postImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        getting post react count && checking if the user already liked the post
        databaseReference.child("Reacts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TODO: get total length
//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
                List<String> likeSize = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    String userId = snap.getKey();
                    likeSize.add(userId);

                    if (userId.equals(currentUser)){
                        binding.postNonReact.setVisibility(View.GONE);
                        binding.postReacted.setVisibility(View.VISIBLE);
                    }
                }

                try {
                    totalLikes = likeSize.size();
                    binding.postReactCount.setText(likeSize.size()+"");
                }catch (Exception e){
                    binding.postReactCount.setText("0");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//comment count
        databaseReference.child("Comments").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TODO: get total length
//                Log.i("TAG", "Notification snapshot: "+ snapshot.getChildren().toString());
                List<String> commentsize = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    String userId = snap.getKey();
                    commentsize.add(userId);


                }

                try {
                    comment = commentsize.size();
                    binding.postCommentCount.setText(commentsize.size()+"");
                }catch (Exception e){
                    binding.postReactCount.setText("0");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







//        Handling commented event

        binding.commentSubmitBtn.setOnClickListener(view ->{

            String comment = binding.commentBox.getText().toString().trim();



            if (!comment.isEmpty()){
                long commentTimeMillis = System.currentTimeMillis();




                Map<String, Object> comMap = new HashMap<>();
                comMap.put("postID", postId);
                comMap.put("commenterName", currentUserName);
                comMap.put("commenterImg", currentProfileImg);
                comMap.put("mainComment", comment);
                comMap.put("commentTimeMillis", commentTimeMillis);
                databaseReference.child("Comments").child(postId).child(currentUser + String.valueOf(commentTimeMillis))
                        .setValue(comMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Map<String, Object> notiMap = new HashMap<>();
                            long timeMillis = System.currentTimeMillis();
                            notiMap.put("postID", postId);
                            notiMap.put("performerID", currentUser); // performer means who gave the react!
                            notiMap.put("notificationTxt", "comment");
                            notiMap.put("notiTimeMillis", timeMillis);
                            notiMap.put("isClicked", false);
                            notiMap.put("isSeen", false);
                            databaseReference.child("Notifications").child(postId).child(currentUser+"com")
                                    .setValue(notiMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        binding.commentBox.setText("");

                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Error occurred!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
            }
        });





//comment setup to adapter
        databaseReference.child("Comments").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PostCommentModel postCommentModel=dataSnapshot.getValue(PostCommentModel.class);

                    if (postCommentModel!=null){
                        commentList.add(postCommentModel);


                    }
                }

                CommentAdapter commentAdapter=new CommentAdapter(commentList,getContext());
                binding.commentRecycler.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        binding.writerProfileImg.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ContainerActivity.class);
            intent.putExtra("imgClicked", true);
            startActivity(intent);
            //    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ProfileFragment()).commit();
        });






//        Handling post reacts
        binding.postNonReact.setOnClickListener(view -> {
//            TODO: If the post was already reacted, delete the react data
            binding.postNonReact.setVisibility(View.GONE);
            binding.postReacted.setVisibility(View.VISIBLE);

            Map<String, Object> reactMap = new HashMap<>();
            reactMap.put("postID", postId);
            reactMap.put("reactorUserID", currentUser);

            databaseReference.child("Reacts").child(postId).child(currentUser).setValue(reactMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        totalLikes = totalLikes + 1;
                        binding.postReactCount.setText(totalLikes+"");
//                        saving data to the notification table
                        Map<String, Object> notiMap = new HashMap<>();
                        long timeMillis = System.currentTimeMillis();
                        notiMap.put("postID", postId);
                        notiMap.put("performerID", currentUser); // performer means who gave the react!
                        notiMap.put("notificationTxt", "react");
                        notiMap.put("notiTimeMillis", timeMillis);
                        notiMap.put("isClicked", false);
                        notiMap.put("isSeen", false);
                        databaseReference.child("Notifications").child(postId).child(currentUser).setValue(notiMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
//                                    totalLikes = totalLikes + 1;
//                                    binding.postReactCount.setText(totalLikes+"");


                                }
                                else {
                                    Toast.makeText(getActivity(), "Error occurred!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            });
        });

        return binding.getRoot();
    }

    @Override
    public void gotoFragmentWithValue(Fragment fragment, String userID) {

    }

    @Override
    public boolean followButtonClickedEvent(String userID) {
        return false;
    }
}
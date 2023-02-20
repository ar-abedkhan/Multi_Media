package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadStoryFragment extends Fragment implements PostListener {
    public ReadStoryFragment() {
    }
    FragmentReadStoryBinding binding;
    String postId, currentUser;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    int totalLikes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReadStoryBinding.inflate(getLayoutInflater(),container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        currentUser = firebaseUser.getUid();
        try {
            postId = getArguments().getString("postID");
        }catch (Exception e){
            Log.i("TAG", "Read Fragment Error: "+e);
        }

//        getting post data from the firebase
        databaseReference.child("Post").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostModel model = snapshot.getValue(PostModel.class);

//                setting values in the view
                binding.mainStory.setText(model.getMainText());
                binding.storyTitle.setTitle(model.getTitle());
                binding.postReactCount.setText(""+model.getPostLike());

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
                                    totalLikes += 1;
                                    binding.postReactCount.setText(totalLikes+"");


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
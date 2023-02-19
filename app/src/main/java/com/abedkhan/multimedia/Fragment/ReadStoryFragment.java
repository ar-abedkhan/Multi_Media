package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentReadStoryBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadStoryFragment extends Fragment implements PostListener {
    public ReadStoryFragment() {
    }
    FragmentReadStoryBinding binding;
    String postId;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReadStoryBinding.inflate(getLayoutInflater(),container,false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
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
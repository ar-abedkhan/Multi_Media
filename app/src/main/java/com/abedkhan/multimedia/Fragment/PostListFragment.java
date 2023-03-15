package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentPostListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostListFragment extends Fragment {

    public PostListFragment() {
    }
       FragmentPostListBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentPostListBinding.inflate(getLayoutInflater(),container,false);

       databaseReference= FirebaseDatabase.getInstance().getReference();
       firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       currentUser=firebaseUser.getUid();














        return binding.getRoot();
    }
}
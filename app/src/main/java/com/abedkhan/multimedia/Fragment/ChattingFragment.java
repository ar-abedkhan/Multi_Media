package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.ChatListModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentChattingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChattingFragment extends Fragment implements PostListener {
    public ChattingFragment() {
    }
    private FragmentChattingBinding binding;
    String currentUserId,postId;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    List<ChatListModel>chatListModelList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentChattingBinding.inflate(getLayoutInflater(),container,false);

        chatListModelList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

//        postId = getArguments().getString("postID");

        if (firebaseUser!=null){
            currentUserId=firebaseUser.getUid();
        }


//            currentUserName = getArguments().getString("ownerFullName");






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
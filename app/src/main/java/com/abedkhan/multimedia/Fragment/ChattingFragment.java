package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Adapters.ChatAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.ChatListModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentChattingBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChattingFragment extends Fragment implements PostListener {
    public ChattingFragment() {
    }
    private FragmentChattingBinding binding;
    String currentUserId,othersUserId;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    List<ChatListModel>chatListModelList;
    Intent intent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentChattingBinding.inflate(getLayoutInflater(),container,false);

        chatListModelList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

//        postId = getArguments().getString("postID"

        intent=getActivity().getIntent();
        othersUserId=intent.getStringExtra("visitor");

        if (firebaseUser!=null){
            currentUserId=firebaseUser.getUid();
        }



//.............TODO: vai ekhane dekhiyen to others id ki perectly ashce naki.. crash kore child bole others id te pblm...................





databaseReference.child("User").child(currentUserId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        UserModel userModel=snapshot.getValue(UserModel.class);
        if (userModel !=null) {
            binding.profilename.setText(userModel.getFullName());
//            binding.chattimeTv.setText();
            Glide.with(requireContext()).load(userModel.getProfileImgUrl())
                    .placeholder(R.drawable.ic_baseline_person_24).into(binding.profileimg);

              }
        }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




//.................message send and recive.............

        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    ChatListModel chatModel = dataSnapshot.getValue(ChatListModel.class);

                    assert chatModel != null;
                    if (
                            chatModel.getReceiverId().equals(currentUserId) && chatModel.getSenderId().equals(othersUserId)
                                    ||
                                    chatModel.getReceiverId().equals(othersUserId) && chatModel.getSenderId().equals(currentUserId)
                    ) {
                        chatListModelList.add(chatModel);
                    }
                }
                setChattoUi(chatListModelList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//...............on message send button clicked.......................
        binding.messagesendbtn.setOnClickListener(view -> {

            if (binding.sendmessage.equals("")){
                Toast.makeText(requireContext(), "Write a message", Toast.LENGTH_SHORT).show();
            }else {
                messageSend();
            }

        });
















        return binding.getRoot();
    }







    private void messageSend() {

        String currentTimMilis = String.valueOf(System.currentTimeMillis());

        String message =binding.sendmessage.getText().toString().trim();
        String chatId =databaseReference.push().getKey();


        ChatListModel chatModel =new ChatListModel(currentTimMilis,othersUserId,message,currentTimMilis,chatId);

        databaseReference.child("chat").child(chatId).setValue(chatModel).addOnSuccessListener(unused -> {
            binding.sendmessage.setText("");
            Toast.makeText(requireContext(), "message send", Toast.LENGTH_SHORT).show();

        });





    }










    private void setChattoUi(List<ChatListModel> chatModelList) {

        ChatAdapter chatAdapter=new ChatAdapter(chatModelList,getContext(),currentUserId);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(requireContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setReverseLayout(true);
//        binding.chatRecycler.scrollToPosition(chatAdapter.getItemCount()-1);
//        linearLayoutManager.scrollToPosition(0);
        binding.chatRecycler.setAdapter(chatAdapter);

    }


    @Override
    public void gotoFragmentWithValue(Fragment fragment, String userID) {

    }

    @Override
    public boolean followButtonClickedEvent(String userID) {
        return false;
    }
}
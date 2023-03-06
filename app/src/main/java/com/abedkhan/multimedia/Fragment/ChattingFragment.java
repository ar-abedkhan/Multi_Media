package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Activities.MainActivity;
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

public class ChattingFragment extends Fragment {
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

//        othersUserId = getArguments().getString("visitor");

        intent=getActivity().getIntent();
        othersUserId=intent.getStringExtra("visitor");

        if (firebaseUser!=null){
            currentUserId=firebaseUser.getUid();
        }


//setting user data to chat ui...........
databaseReference.child("User").child(othersUserId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        UserModel userModel=snapshot.getValue(UserModel.class);
        if (userModel !=null) {
            binding.profilename.setText(userModel.getFullName());
//            binding.chattimeTv.setText();
            Glide.with(getActivity().getApplicationContext()).load(userModel.getProfileImgUrl())
                    .placeholder(R.drawable.ic_baseline_person_24).into(binding.profileimg);

              }
        }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});





//...............on message send button clicked.......................


//            binding.messagesendbtn.setEnabled(false);
            binding.messagesendbtn.setVisibility(View.VISIBLE);



            binding.messagesendbtn.setOnClickListener(view -> {

                String mess = binding.sendmessage.getText().toString().trim();


                if (!mess.equals("")) {

                    messageSend();
                    Toast.makeText(requireContext(), "Write a message", Toast.LENGTH_SHORT).show();

                } else {

                }

            });






//try {
//    binding.sendmessage.equals(null);
//    binding.messagesendbtn.setVisibility(View.GONE);
//
//
//}catch (Exception e){
//    binding.messagesendbtn.setVisibility(View.VISIBLE);
//
//    binding.messagesendbtn.setOnClickListener(view -> {
//
//        if (binding.sendmessage.equals("")) {
//            Toast.makeText(requireContext(), "Write a message", Toast.LENGTH_SHORT).show();
//        } else {
//            messageSend();
//        }
//
//    });
//}







        //.................message send and receive.............

        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelList.clear();
                List<ChatListModel> tempoChatList = new ArrayList<>();
//                int numCounter = 0;

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    ChatListModel chatModel = dataSnapshot.getValue(ChatListModel.class);
                    tempoChatList.add(chatModel);

                    assert chatModel != null;
//                    Log.i("TAG", "Chatting Fragment others ID: "+ othersUserId);
                    try {
                        if (
                                chatModel.getReceiverId().equals(currentUserId) && chatModel.getSenderId().equals(othersUserId)
                                        ||
                                        chatModel.getReceiverId().equals(othersUserId) && chatModel.getSenderId().equals(currentUserId)
                        ) {
                            chatListModelList.add(chatModel);
                        }
                    }
                    catch (Exception e){
                        if (
                                currentUserId.equals(tempoChatList.get(0).getReceiverId())
                                        ||
                                        currentUserId.equals( tempoChatList.get(0).getSenderId())
                        ) {
                            chatListModelList.add(chatModel);
                        }
                    }

//                    numCounter += 1;


                }
                setChattoUi(chatListModelList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//
//        binding.backbtn.setOnClickListener(view -> {
//            intent.putExtra("isMessage", true);
//
////            intent.putExtra("visitor",userModel.getUserID());
//            Log.i("visitorID", "onCreateView: "+intent);
//
//            startActivity(intent);
//       getChildFragmentManager().beginTransaction().replace(R.id.containerFrame,new MessageFragment()).addToBackStack(null).commit();//
//
//        });


        //chat fragment to profile
binding.profileimg.setOnClickListener(view -> {

    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
    ProfileFragment profileFragment = new ProfileFragment();
    Bundle bundle = new Bundle();
    bundle.putString("VisitedUserID", othersUserId);
    profileFragment.setArguments(bundle);
    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, profileFragment).addToBackStack(null).commit();
});
binding.profilename.setOnClickListener(view -> {

    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
    ProfileFragment profileFragment = new ProfileFragment();
    Bundle bundle = new Bundle();
    bundle.putString("VisitedUserID", othersUserId);
    profileFragment.setArguments(bundle);
    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, profileFragment).addToBackStack(null).commit();
});









        return binding.getRoot();
    }







    private void messageSend() {

        long currentTimMillis = System.currentTimeMillis();

        String message =binding.sendmessage.getText().toString().trim();
        String chatId =databaseReference.push().getKey();


        ChatListModel chatModel =new ChatListModel(currentUserId,othersUserId,message,chatId, currentTimMillis);

        databaseReference.child("chat").child(chatId).setValue(chatModel).addOnSuccessListener(unused -> {
            binding.sendmessage.setText("");
            Toast.makeText(requireContext(), "message send", Toast.LENGTH_SHORT).show();

        });





    }



//set message adapter...........
    private void setChattoUi(List<ChatListModel> chatModelList) {

        ChatAdapter chatAdapter=new ChatAdapter(chatModelList,getContext(),currentUserId);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setReverseLayout(true);
//        binding.chatRecycler.scrollToPosition(chatAdapter.getItemCount()-1);
//        linearLayoutManager.scrollToPosition(0);
        binding.chatRecycler.setAdapter(chatAdapter);

    }

}
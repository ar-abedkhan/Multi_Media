package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Adapters.ChatListAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.ChatListModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentMessageBinding;
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

public class MessageFragment extends Fragment implements PostListener{
    public MessageFragment() {
    }

      FragmentMessageBinding binding;
    List<ChatListModel> chatListModelList;
    List<UserModel> userModelList;
     DatabaseReference databaseReference;
     FirebaseUser firebaseUser;
     String currentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMessageBinding.inflate(getLayoutInflater(),container,false);

chatListModelList=new ArrayList<>();
userModelList=new ArrayList<>();

firebaseUser= FirebaseAuth.getInstance().getCurrentUser();











databaseReference= FirebaseDatabase.getInstance().getReference("User");


databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        userModelList.clear();

        for (DataSnapshot dataSnapshot:snapshot.getChildren()){

            UserModel userModel=dataSnapshot.getValue(UserModel.class);
            if (!userModel.getUserID().equals(firebaseUser.getUid())) {
                userModelList.add(userModel);
            }
        }
        ChatListAdapter chatListAdapter=new ChatListAdapter(userModelList,getContext(),MessageFragment.this,true);
        binding.chatListRecyclerView.setAdapter(chatListAdapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




binding.home.setOnClickListener(view -> {
    startActivity(new Intent(requireContext(), MainActivity.class));
});

binding.backBtn.setOnClickListener(view -> {
    startActivity(new Intent(requireContext(), ContainerActivity.class));

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








 //showing ststus............
    private void status(String status){
        databaseReference=FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        HashMap<String , Object> hashMap=new HashMap<>();
        hashMap.put("status",status);
        databaseReference.updateChildren(hashMap);

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
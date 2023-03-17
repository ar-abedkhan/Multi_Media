package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.PostAdapter;
import com.abedkhan.multimedia.Adapters.UserAdapter;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentPostListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostListFragment extends Fragment {

    public PostListFragment() {
    }
       FragmentPostListBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUser;
    List<PostModel>postModelList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentPostListBinding.inflate(getLayoutInflater(),container,false);

       databaseReference= FirebaseDatabase.getInstance().getReference();
       firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       currentUser=firebaseUser.getUid();
       postModelList=new ArrayList<>();



       databaseReference.child("post").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                   PostModel postModel=dataSnapshot.getValue(PostModel.class);

                   if (postModel.getPostID().equals(currentUser)){
                       postModelList.add(postModel);
                   }
               }
               try {

//                   PostAdapter adapter = new PostAdapter(getContext(), postModelList,this, currentUser);
//                   binding.postRecycler.setAdapter(adapter);

               }catch (Exception exception){

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });










        return binding.getRoot();
    }
}
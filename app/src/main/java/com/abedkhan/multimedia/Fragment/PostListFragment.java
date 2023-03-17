package com.abedkhan.multimedia.Fragment;

import static com.abedkhan.multimedia.Activities.ContainerActivity.requestedIdForPost;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.PostAdapter;
import com.abedkhan.multimedia.Adapters.UserAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
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

public class PostListFragment extends Fragment implements PostListener {

    public PostListFragment() {
    }
       FragmentPostListBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUser;
    String postOwnerID;
    List<PostModel>postModelList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentPostListBinding.inflate(getLayoutInflater(),container,false);

       databaseReference= FirebaseDatabase.getInstance().getReference();

       postModelList=new ArrayList<>();

        try {
            if (!requestedIdForPost.isEmpty()){
                postOwnerID = requestedIdForPost;

            }
        }catch (Exception exception){
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            currentUser=firebaseUser.getUid();
            postOwnerID = currentUser;
        }


       databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                   String postId = dataSnapshot.getKey();

                   databaseReference.child("Post").child(postId).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {

                               PostModel postModel= snapshot.getValue(PostModel.class);

                               if (postModel.getOwnerID().equals(postOwnerID)){
//                                   Log.i("TAG", "TRUE ");
                                   postModelList.add(postModel);
                                   try {

//                                       Log.i("TAG", "Post model List (PLF)--1: "+postModelList.size());
                                       setDataToView(postModelList);

                                   }catch (Exception exception){

                                   }
//                               }
                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                   try {

                       Log.i("TAG", "Post model List (PLF): "+postModelList.size());
                       setDataToView(postModelList);

                   }catch (Exception exception){

                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });










        return binding.getRoot();
    }

    private void setDataToView(List<PostModel> postModelList) {
        PostAdapter adapter = new PostAdapter(getContext(), postModelList, this, currentUser);
        binding.postRecycler.setAdapter(adapter);
    }

    @Override
    public void gotoFragmentWithValue(Fragment fragment, String userID) {

    }

    @Override
    public boolean followButtonClickedEvent(String userID) {
        return false;
    }
}
package com.abedkhan.multimedia.Fragment;

import static com.abedkhan.multimedia.Activities.ContainerActivity.requestedIdForPost;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.FollowingFollowerAdapter;
import com.abedkhan.multimedia.Model.FollowerFollowingModel;
import com.abedkhan.multimedia.databinding.FragmentFollowerListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FollowerListFragment extends Fragment {

    public FollowerListFragment() {
    }


    FragmentFollowerListBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUser;
    String presentVisitedID;
    List<FollowerFollowingModel> followerModels;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=FragmentFollowerListBinding.inflate(getLayoutInflater(),container,false);


        databaseReference= FirebaseDatabase.getInstance().getReference();
//        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        currentUser=firebaseUser.getUid();
        followerModels =new ArrayList<>();



        try {
            if (!requestedIdForPost.isEmpty()){
                presentVisitedID = requestedIdForPost;

            }
        }catch (Exception exception){
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            currentUser=firebaseUser.getUid();
            presentVisitedID = currentUser;
        }



        databaseReference.child("Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key = dataSnapshot.getKey();

                    databaseReference.child("Followers").child(key).child(presentVisitedID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            FollowerFollowingModel model= snapshot.getValue(FollowerFollowingModel.class);
                            if (!followerModels.contains(model)) {
                                followerModels.add(model);
                            }

                            try {

//                                Log.i("TAG", "Following models size: "+ followerModels.size());
                                FollowingFollowerAdapter adapter=new FollowingFollowerAdapter(requireContext(), followerModels);
                                binding.followerRecycler.setAdapter(adapter);

                            }catch (Exception exception){

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        return binding.getRoot();
    }
}
package com.abedkhan.multimedia.Fragment;

import static com.abedkhan.multimedia.Activities.ContainerActivity.requestedIdForPost;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.FollowingFollowerAdapter;
import com.abedkhan.multimedia.Adapters.UserAdapter;
import com.abedkhan.multimedia.Model.FollowerFollowingModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentFollowinglistBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FollowinglistFragment extends Fragment {

    public FollowinglistFragment() {
    }

    FragmentFollowinglistBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUser;
    String presentVisitedID;
    List<FollowerFollowingModel> followingModels;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=FragmentFollowinglistBinding.inflate(getLayoutInflater(),container,false);

        databaseReference= FirebaseDatabase.getInstance().getReference();
//        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        currentUser=firebaseUser.getUid();
        followingModels=new ArrayList<>();



        try {
            if (!requestedIdForPost.isEmpty()){
                presentVisitedID = requestedIdForPost;

            }
        }catch (Exception exception){
            firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            currentUser=firebaseUser.getUid();
            presentVisitedID = currentUser;
        }

        databaseReference.child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key = dataSnapshot.getKey();

                    databaseReference.child("Following").child(key).child(presentVisitedID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            FollowerFollowingModel model= snapshot.getValue(FollowerFollowingModel.class);
                            if (!followingModels.contains(model)) {
                                followingModels.add(model);
                            }

                            try {

//                                Log.i("TAG", "Following models size: "+ followerModels.size());
                                FollowingFollowerAdapter adapter=new FollowingFollowerAdapter(requireContext(), followingModels);
                                binding.followingRecycler.setAdapter(adapter);

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
package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.NotificationAdapter;
import com.abedkhan.multimedia.Model.NotificationModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    public NotificationFragment() {
        // Required empty public constructor
    }

    FragmentNotificationBinding binding;
    List<NotificationModel> notiList;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(getLayoutInflater(), container, false);
        notiList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = firebaseUser.getUid();

        databaseReference.child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    String postId = snap.getKey();
//                    Log.i("TAG", "Notification post id: "+ postId);
                    databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap2: snapshot.getChildren()) {
                                String performerID = snap2.getKey();
                                PostModel postModel = (PostModel) snap2.getValue(PostModel.class);
                                if (postModel.getOwnerID().equals(currentUser)){
                                    databaseReference.child("Notifications").child(postId).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot snap3: snapshot.getChildren()) {
                                                NotificationModel model = snap3.getValue(NotificationModel.class);
                                                Log.i("TAG", "Notification@:- "+ model.getNotificationTxt());
                                                notiList.add(model);

                                                Log.i("TAG", "Notification@:--- "+ notiList.size());
                                            }

                                            NotificationAdapter adapter = new NotificationAdapter(getActivity(), notiList);
                                            Log.i("TAG", "onDataChange: "+notiList.size());
                                            binding.notificationRecycler.setAdapter(adapter);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
//
//                NotificationAdapter adapter = new NotificationAdapter(getActivity(), notiList);
//                Log.i("TAG", "onDataChange: "+notiList.size());
//                binding.notificationRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}
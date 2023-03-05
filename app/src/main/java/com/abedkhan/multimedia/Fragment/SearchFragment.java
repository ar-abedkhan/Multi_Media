package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Adapters.UserAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSearchBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    FragmentSearchBinding binding;
    List<UserModel>userModelList;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    UserAdapter userAdapter;
    boolean isClicked;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater(), container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userModelList=new ArrayList<>();




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

                UserAdapter userAdapter=new UserAdapter(requireContext(),userModelList);
                binding.postRecycler.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        binding.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                    userAdapter.getFilter().filter(s);
//
//                    return false;
//            }
//        });






        if (isClicked){
            binding.backBtn.setVisibility(View.GONE);
        }






//        FirebaseRecyclerOptions<UserModel> options =
//                new FirebaseRecyclerOptions.Builder<UserModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("fullName"),UserModel.class)
//                        .build();









//        AutoCompleteTextView searchBox = findViewById(R.id.searchBox);
//        binding.searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    // Perform your search action here
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//



//        handling back button
        binding.backBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(new Intent(getActivity(), MainActivity.class));
        });

        return binding.getRoot();
    }




}
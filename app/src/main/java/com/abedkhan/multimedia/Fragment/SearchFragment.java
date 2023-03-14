package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Adapters.PostAdapter;
import com.abedkhan.multimedia.Adapters.UserAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSearchBinding;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    FragmentSearchBinding binding;
    List<UserModel>userModelList;
    List<PostModel>postModelList;
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
        postModelList=new ArrayList<>();




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

//                ,,,,,,Maybe we need to create a new adapter

                try {

                    UserAdapter userAdapter=new UserAdapter(requireContext(),userModelList);
                    binding.userSearchRecycler.setAdapter(userAdapter);

                }catch (Exception exception){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        binding.search.setOnClickListener(view -> {

            binding.backBtn.setVisibility(View.GONE);
            binding.search.setVisibility(View.GONE);
            binding.searchView.setVisibility(View.VISIBLE);



        });





        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchUser(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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



//for post search.............
    //TODO: post search dekhiyen parle...........
//    private void searchPost(String s) {
//
//            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//            Query query=FirebaseDatabase.getInstance().getReference("User").child("Post").orderByChild("title")
//                    .startAt(s)
//                    .endAt(s+"\uf8ff");
//
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    postModelList.clear();
//
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        PostModel postModel=dataSnapshot.getValue(PostModel.class);
//
//                        assert postModel != null;
//                        assert firebaseUser != null;
//                        if (!postModel.getPostID().equals("")) {
//                            postModelList.add(postModel);
//                        }
//                    }
//                    try {
//
//                        PostAdapter postAdapter=new PostAdapter(requireActivity(),postModelList,this,firebaseUser.getUid());
//                        binding.postRecycler.setAdapter(postAdapter);
//
//                    }catch (Exception exception){
//
//                    }
////        }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//
//        }










    //search handeling.........
    private void searchUser(String s) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference("User").orderByChild("fullName")
                .startAt(s)

                .endAt(s+"\uf8ff");

query.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

//        if (binding.searchView.getText().toString().equals("")) {


            userModelList.clear();

            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);

                assert userModel != null;
                assert firebaseUser != null;
                if (!userModel.getUserID().equals(firebaseUser.getUid())) {
                    userModelList.add(userModel);
                }
            }
            try {

                UserAdapter userAdapter=new UserAdapter(requireContext(),userModelList);
                binding.userSearchRecycler.setAdapter(userAdapter);

            }catch (Exception exception){

            }
//        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }

}
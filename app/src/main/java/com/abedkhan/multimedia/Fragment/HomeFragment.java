package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Activities.ShoppingMainActivity;
import com.abedkhan.multimedia.Adapters.CetagoryFragmentAdapter;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentHomeBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
FragmentHomeBinding binding;
FragmentManager fragmentManager;
    private Fragment fragment;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String currentUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=FragmentHomeBinding.inflate(getLayoutInflater(),container,false);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        currentUser=firebaseUser.getUid();


// home page er toolbar category ekhane set kora hoise...

        fragmentManager=getChildFragmentManager();
        CetagoryFragmentAdapter categoryAdapter = new CetagoryFragmentAdapter(fragmentManager,100);
        binding.cetagoryViewpager.setAdapter(categoryAdapter);
        binding.cetagoryTabLayout.setupWithViewPager(binding.cetagoryViewpager);






        databaseReference.child("User").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel=snapshot.getValue(UserModel.class);

                if (userModel!=null){
//TODO: ekhane context dekhen jamela kore.................
//
//                    Glide.with(requireActivity()).load(userModel.getProfileImgUrl())
//                            .placeholder(R.drawable.lightning_tree).into(binding.userProfile);
                }
                try {
                    Glide.with(requireActivity()).load(userModel.getProfileImgUrl())
                            .placeholder(R.drawable.lightning_tree).into(binding.userProfile);
                }catch (Exception exception){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //        user profile clicked
        binding.userProfile.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("isProfileCLicked", true);
            startActivity(intent);
            //    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ProfileFragment()).commit();
        });


        binding.searchBar.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("isSearchClicked", true);
            startActivity(intent);
            //    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SearchFragment()).commit();
        });


        binding.shopping.setOnClickListener(view -> {
            startActivity(new Intent(requireContext(), ShoppingMainActivity.class));
        });



        return binding.getRoot();
    }

    //fragment placem,ent ...............
    private void replace(Fragment fragment, boolean isToolActive) {
        if (isToolActive){
            binding.tool.setVisibility(View.GONE);
        }
        else {
            binding.tool.setVisibility(View.VISIBLE);
        }

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();



    }
}
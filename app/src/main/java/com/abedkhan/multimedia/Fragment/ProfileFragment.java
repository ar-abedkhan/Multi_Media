package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Activities.ShoppingMainActivity;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    private FragmentProfileBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        binding=FragmentProfileBinding.inflate(getLayoutInflater(),container,false);
//
//floatingActionButton=container.findViewById(R.id.menu_item);
//
//
//
//       floatingActionButton.setOnClickListener(view -> {
//            startActivity(new Intent(requireContext(),ShoppingMainActivity.class));
//        });
////
        return inflater.inflate(R.layout.fragment_profile, container, false);
//        return binding.getRoot();
    }
}
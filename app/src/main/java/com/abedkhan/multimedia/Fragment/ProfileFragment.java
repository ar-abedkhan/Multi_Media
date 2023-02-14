package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Activities.ShoppingMainActivity;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentProfileBinding;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    private FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(getLayoutInflater(),container,false);

        //        user profile clicked
        binding.settings.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("isSettingsClicked", true);
            startActivity(intent);
        });

        binding.message.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("isMessageClicked", true);
            startActivity(intent);
        });

        binding.addPost.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.putExtra("isAddClicked", true);
            startActivity(intent);
        });




//        return inflater.inflate(R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }
}
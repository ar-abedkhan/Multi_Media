package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentMainHomeBinding;

public class MainHomeFragment extends Fragment {
    public MainHomeFragment() {
    }

    FragmentMainHomeBinding binding;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMainHomeBinding.inflate(getLayoutInflater(),container,false);


        return binding.getRoot();
    }
}
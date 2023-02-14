package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentProfileEditBinding;


public class profileEditFragment extends Fragment {

    public profileEditFragment() {
    }
    FragmentProfileEditBinding binding;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileEditBinding.inflate(getLayoutInflater(),container,false);











        return binding.getRoot();
    }
}
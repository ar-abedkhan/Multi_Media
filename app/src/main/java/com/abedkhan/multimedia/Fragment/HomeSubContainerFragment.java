package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentHomeSubContainerBinding;

public class HomeSubContainerFragment extends Fragment {

    public HomeSubContainerFragment() {
        // Required empty public constructor
    }

    FragmentHomeSubContainerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeSubContainerBinding.inflate(getLayoutInflater(),container,false);

        if (getArguments() != null) {
            int position = getArguments().getInt("position");
            Log.i("TAG", "Position(Home sub container): "+position);


            String p=String.valueOf(position);

            binding.text.setText(p);



        }
        return binding.getRoot();
    }
}
package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.CetagoryFragmentAdapter;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
FragmentHomeBinding binding;
FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=FragmentHomeBinding.inflate(getLayoutInflater(),container,false);



//TODO: vai home page er toolbar cetagory ekhane set kora hoise...

        fragmentManager=getChildFragmentManager();
        CetagoryFragmentAdapter cetagoryAdapter = new CetagoryFragmentAdapter(fragmentManager,100);
        binding.cetagoryViewpager.setAdapter(cetagoryAdapter);
        binding.cetagoryTabLayout.setupWithViewPager(binding.cetagoryViewpager);




return binding.getRoot();
    }
}
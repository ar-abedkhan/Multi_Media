package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentReadStoryBinding;

public class ReadStoryFragment extends Fragment implements PostListener {
    public ReadStoryFragment() {
    }
FragmentReadStoryBinding binding;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReadStoryBinding.inflate(getLayoutInflater(),container,false);


















        return binding.getRoot();
    }

    @Override
    public void gotoFragmentWithValue(Fragment fragment, String userID) {

    }

    @Override
    public boolean followButtonClickedEvent(String userID) {
        return false;
    }
}
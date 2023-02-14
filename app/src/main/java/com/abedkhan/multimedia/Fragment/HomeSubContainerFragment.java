package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.R;

public class HomeSubContainerFragment extends Fragment {

    public HomeSubContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            int position = getArguments().getInt("position");
            Log.i("TAG", "Position(Home sub container): "+position);
        }

        return inflater.inflate(R.layout.fragment_home_sub_container, container, false);
    }
}
package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.R;
public class AllCetagoryStoryFragment extends Fragment {
    public AllCetagoryStoryFragment() {
        // Required empty public constructor
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_all_cetagory_story, container, false);
    }
}
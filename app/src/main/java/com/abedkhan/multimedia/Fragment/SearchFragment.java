package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    public SearchFragment() {
        // Required empty public constructor
    }

    FragmentSearchBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater(), container, false);

//        AutoCompleteTextView searchBox = findViewById(R.id.searchBox);
//        binding.searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    // Perform your search action here
//                    return true;
//                }
//                return false;
//            }
//        });

//        handling back button
        binding.backBtn.setOnClickListener(view -> {
//            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(new Intent(getActivity(), MainActivity.class));
        });

        return binding.getRoot();
    }
}
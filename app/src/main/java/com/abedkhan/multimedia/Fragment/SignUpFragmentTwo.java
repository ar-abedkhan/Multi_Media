package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSignUpTwoBinding;

public class SignUpFragmentTwo extends Fragment {

    public SignUpFragmentTwo() {
        // Required empty public constructor
    }


    FragmentSignUpTwoBinding binding;
    String fullName, userName, email, gender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpTwoBinding.inflate(getLayoutInflater(), container, false);

//        getting data from the fragment
        fullName = getArguments().getString("Signup_FullName");
        userName = getArguments().getString("Signup_UserName");
        email = getArguments().getString("Signup_Email");
        gender = getArguments().getString("Signup_Gender");

//        Log.i("TAG", "Signup two:-> "+fullName+"\nUserName: "+userName+"\nEmail: "+email+"\nGender:"+ gender);

//        Back Button handling
        binding.backBtn.setOnClickListener(view -> {
            //            --going to the signup page two
            Fragment fragment = new SignUpFragmentOne();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return binding.getRoot();
    }
}
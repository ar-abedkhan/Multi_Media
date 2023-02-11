package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSignUpOneBinding;


public class SignUpFragmentOne extends Fragment {
    public SignUpFragmentOne() {
        // Required empty public constructor
    }

    FragmentSignUpOneBinding binding;
    String fullName, userName, email;
    String selectedGender="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpOneBinding.inflate(getLayoutInflater(), container, false);

//        Gender radio group
        binding.genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton genderButton = getActivity().findViewById(id);
                selectedGender = genderButton.getText().toString();
            }
        });

//        handling next button clicked
        binding.nextButton.setOnClickListener(view -> {
            fullName = binding.signupFullName.getText().toString().trim();
            userName = binding.signupUsername.getText().toString().trim();
            email = binding.signupEmail.getText().toString().trim();

            if (fullName.isEmpty()){
                binding.signupFullName.setError("Field cannot be empty!");
            } else if (userName.isEmpty()) {
                binding.signupUsername.setError("Field cannot be empty!");
            } else if (email.isEmpty()) {
                binding.signupEmail.setError("Field cannot be empty!");
            } else if (selectedGender.isEmpty()) {
                Toast.makeText(getActivity(), "Please select a gender!", Toast.LENGTH_SHORT).show();
            } else {
                Fragment fragment = new SignUpFragmentTwo();
//                passing data to fragment
                Bundle bundle = new Bundle();
                bundle.putString("Signup_FullName", fullName);
                bundle.putString("Signup_UserName", userName);
                bundle.putString("Signup_Email", email);
                bundle.putString("Signup_Gender", selectedGender);
                fragment.setArguments(bundle);


//            --going to the signup page two

                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return binding.getRoot();
    }
}
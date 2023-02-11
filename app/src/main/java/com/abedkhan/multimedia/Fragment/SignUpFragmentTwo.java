package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSignUpTwoBinding;

public class SignUpFragmentTwo extends Fragment {

    public SignUpFragmentTwo() {
        // Required empty public constructor
    }


    FragmentSignUpTwoBinding binding;
    String fullName, userName, email, gender, dob, password;
    long idCreationTimeMillis;
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

//        Register Button handling
        binding.registerBtn.setOnClickListener(view -> {
            dob = getDOBFromView();
            password = binding.signupPassword.getText().toString();
            if (dob.isEmpty()){
                Toast.makeText(getActivity(), "Date cannot be empty!", Toast.LENGTH_SHORT).show();
            }
            else if (password.isEmpty()) {
//                Toast.makeText(getActivity(), "Field  cannot be empty!", Toast.LENGTH_SHORT).show();
                binding.signupPassword.setError("Field cannot be empty!");
            }
            else if (binding.signupRePassword.getText().toString().isEmpty()) {
//                Toast.makeText(getActivity(), "Date cannot be empty!", Toast.LENGTH_SHORT).show();
                binding.signupRePassword.setError("Field cannot be empty!");
            }
            else if (!password.equals(binding.signupRePassword.getText().toString())) {
//                Toast.makeText(getActivity(), "Password does not matched!", Toast.LENGTH_SHORT).show();
                binding.signupRePassword.setError("Password does not matched!");
            }else {
                idCreationTimeMillis = System.currentTimeMillis();
                saveDataToOnlineStorage();
            }
        });

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

    private void saveDataToOnlineStorage() {
        Log.i("TAG", "Saving data to firebase! ");
    }

    private String getDOBFromView() {
        int date = binding.agePicker.getDayOfMonth();
        int month = binding.agePicker.getMonth()+1;
        int year = binding.agePicker.getYear();
        return  date+"/"+month+"/"+year;
    }
}
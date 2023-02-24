package com.abedkhan.multimedia.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentSignUpTwoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragmentTwo extends Fragment {

    public SignUpFragmentTwo() {
        // Required empty public constructor
    }


    FragmentSignUpTwoBinding binding;
    String fullName, userName, email, gender, dob, password;

//    followerCount,followingCount,publishedPosCount,savePostCount;

    long idCreationTimeMillis;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpTwoBinding.inflate(getLayoutInflater(), container, false);

//        Firebase settings
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

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


//        Handling Login option selection
        binding.signupLoginButton.setOnClickListener(view -> {
            Fragment fragment = new LoginFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerFrame, fragment);
            transaction.commit();
        });

        return binding.getRoot();
    }

    private String getDOBFromView() {
        int date = binding.agePicker.getDayOfMonth();
        int month = binding.agePicker.getMonth()+1;
        int year = binding.agePicker.getYear();
        return  date+"/"+month+"/"+year;
    }

//    Saving data to the firebase
    private void saveDataToOnlineStorage() {
//        databaseReference.child("User")

        binding.registerBtn.setVisibility(View.GONE);
        binding.progressbar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//                saving data to the realtime database
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("userID", firebaseUser.getUid());
                userMap.put("password", password);
                userMap.put("profileImgUrl", "");
                userMap.put("fullName", fullName);
                userMap.put("userName", userName);
                userMap.put("email", email);
                userMap.put("gender", gender);
                userMap.put("dateOfBirth", dob);
                userMap.put("idCreationTimeMillis", idCreationTimeMillis);
                userMap.put("userBio", "");
                userMap.put("profession", "");
                userMap.put("livingCountry", "");
                userMap.put("livingCity", "");
                userMap.put("followerCount", "");
                userMap.put("followingCount", "");
                userMap.put("publishedPostCount", "");
                userMap.put("SavePostCount", "");

                databaseReference.child("User").child(firebaseUser.getUid()).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Data saved successfully❤", Toast.LENGTH_LONG).show();

                            //                            TODO: Go to login fragment...
                        }
                        else {
                            showAlert("Error", task.getException().getLocalizedMessage());
                        }
                        binding.registerBtn.setVisibility(View.VISIBLE);
                        binding.progressbar.setVisibility(View.GONE);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showAlert("Failed", e.getLocalizedMessage());
                        binding.registerBtn.setVisibility(View.VISIBLE);
                        binding.progressbar.setVisibility(View.GONE);
                    }
                });
    }

//    Alert
    private void showAlert(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.drawable.warning_icon);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
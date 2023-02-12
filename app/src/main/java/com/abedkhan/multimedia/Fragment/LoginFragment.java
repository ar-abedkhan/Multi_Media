package com.abedkhan.multimedia.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FragmentLoginBinding binding;
    String email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        checking if anyone has already logged in!
        if (firebaseUser != null){
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();

//        handling login button
        binding.loginButton.setOnClickListener(view -> {

            binding.loginButton.setVisibility(View.GONE);
            binding.progressbar.setVisibility(View.VISIBLE);

            email = binding.userMail.getText().toString();
            password = binding.signupPassword.getText().toString();

            if (email.isEmpty()){
                binding.userMail.setError("Field cannot be empty!");
            } else if (password.isEmpty()) {
                binding.signupPassword.setError("Field cannot be empty!");
            }else {
//                logging to the account
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            binding.loginButton.setVisibility(View.VISIBLE);
                            binding.progressbar.setVisibility(View.GONE);

                            Toast.makeText(getContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }else {
                            showAlert("Error!!", task.getException().getLocalizedMessage());

                            binding.loginButton.setVisibility(View.VISIBLE);
                            binding.progressbar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

//        Handling Register option clicked
        binding.registerOption.setOnClickListener(view -> {
            Fragment fragment = new SignUpFragmentOne();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return binding.getRoot();
    }

    private void showAlert(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
package com.abedkhan.multimedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.abedkhan.multimedia.Fragment.ChattingFragment;
import com.abedkhan.multimedia.Fragment.LoginFragment;
import com.abedkhan.multimedia.Fragment.MessageFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.SearchFragment;
import com.abedkhan.multimedia.Fragment.SignUpFragmentOne;
import com.abedkhan.multimedia.Fragment.profileEditFragment;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.ActivityContainerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ContainerActivity extends AppCompatActivity {

    ActivityContainerBinding binding;
    Intent intent;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        databaseReference=FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        try {
            intent = getIntent();

            /*
            * This try catch checks if this activity has opened for the first time or not
            * if "try" does not work, that means the application has been opened for the first time
            * --for the first timers catch block will lead the users to the login page
            * */


            Log.i("TAG", "Inside Try");
            if (intent.getBooleanExtra("isProfileCLicked", false)){
                replace(new ProfileFragment());

            } else if (intent.getBooleanExtra("isSearchClicked",false)) {
                replace(new SearchFragment());

            }else if (intent.getBooleanExtra("settingsClicked",false)) {
                replace(new profileEditFragment());

            }else if (intent.getBooleanExtra("isMessageClicked",false)) {
                replace(new ChattingFragment());

            }else if (intent.getBooleanExtra("isMessage",false)) {
                replace(new MessageFragment());

            }else if (intent.getBooleanExtra("uId",false)) {
                replace(new ProfileFragment());

            }else if (intent.getBooleanExtra("imgClicked", false)) {
                replace(new ProfileFragment());

            }else
            {
                    replace(new LoginFragment());
                }

        }catch (Exception e){
            replace(new LoginFragment());
        }


    }

    private void replace(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, fragment).commit();
    }


    private void status(String status){
        databaseReference= FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        HashMap<String , Object> hashMap=new HashMap<>();
        hashMap.put("status",status);
        databaseReference.updateChildren(hashMap);


    }

    @Override
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }
}
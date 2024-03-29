package com.abedkhan.multimedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.abedkhan.multimedia.Fragment.ChattingFragment;
import com.abedkhan.multimedia.Fragment.FollowerListFragment;
import com.abedkhan.multimedia.Fragment.FollowinglistFragment;
import com.abedkhan.multimedia.Fragment.LoginFragment;
import com.abedkhan.multimedia.Fragment.MessageFragment;
import com.abedkhan.multimedia.Fragment.PostListFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.ReadStoryFragment;
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
    public static String requestedIdForPost;


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

//
//            }else if (intent.getBooleanExtra("postClicked", false)) {
//                replace(new ReadStoryFragment());
//
//
//




            }else if (intent.getBooleanExtra("following", false)) {
//                requestedIdForPost = getIntent().getStringExtra("requestedIdForPost");
                try {
                    requestedIdForPost = getIntent().getStringExtra("requestedIdForPost");
                    Log.i("TAG", "post owner ID: "+ requestedIdForPost);
                }catch (Exception exception){}
                replace(new FollowinglistFragment());

            }else if (intent.getBooleanExtra("follower", false)) {
                try {
                    requestedIdForPost = getIntent().getStringExtra("requestedIdForPost");
                    Log.i("TAG", "post owner ID: "+ requestedIdForPost);
                }catch (Exception exception){}
                replace(new FollowerListFragment());

            }
            else if (intent.getBooleanExtra("postlist", false)) {
                                try {
                    requestedIdForPost = getIntent().getStringExtra("requestedIdForPost");
                    Log.i("TAG", "post owner ID: "+ requestedIdForPost);
                }catch (Exception exception){}
                replace(new PostListFragment());

            } else
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

}
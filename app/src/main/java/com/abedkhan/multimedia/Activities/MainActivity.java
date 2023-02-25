package com.abedkhan.multimedia.Activities;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.abedkhan.multimedia.Fragment.AddPostFragment;
import com.abedkhan.multimedia.Fragment.ChattingFragment;
import com.abedkhan.multimedia.Fragment.HomeFragment;
import com.abedkhan.multimedia.Fragment.NotificationFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.SearchFragment;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.ActivityMainBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/*
* Starting date: 08 February 2023
* This application is created by "Abed" and "Osama"
* @AbedKhan
* @OsamaBinHashim
* */

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        Intent intent1 =getIntent();

        runTimePermission();
//        --Bottom menu meow button
        bottomMeowMenu();




    //        user profile clicked
    binding.userProfile.setOnClickListener(view -> {
        Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
        intent.putExtra("isProfileCLicked", true);
        startActivity(intent);
    //    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ProfileFragment()).commit();
    });


    binding.searchBar.setOnClickListener(view -> {
        Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
        intent.putExtra("isSearchClicked", true);
        startActivity(intent);
    //    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SearchFragment()).commit();
    });


//    binding.shopping.setOnClickListener(view -> {
//        startActivity(new Intent(MainActivity.this,ShoppingMainActivity.class));
//    });


        if (intent1.getBooleanExtra("isAddClicked", false)){
            replace(new AddPostFragment(),false);

        }

//    binding.signupLoginButton.setOnClickListener(view -> {
//        Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
//        intent.putExtra("log", true);
//        startActivity(intent);
//    });


    }

    //bottom Menu setup.............
    private void bottomMeowMenu() {

        meowBottomNavigation=findViewById(R.id.meowBottomNavigation);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home3));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.add1));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.notification));

        meowBottomNavigation.show(1,true);



        replace(new HomeFragment(), false);


        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {


                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment(), false);
//                        isToolActive = false;
                        break;

                    case 2:
                        replace(new AddPostFragment(), true);
//                        isToolActive = true;
                        break;

                    case 3:
                        replace(new NotificationFragment(), true);
//                        isToolActive = false;

                        break;
                }

                return null;
            }
        });


    }


    private void runTimePermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();

    }


    //fragment placem,ent ...............
    private void replace(Fragment fragment, boolean isToolActive) {
        if (isToolActive){
            binding.tool.setVisibility(View.GONE);
        }
        else {
            binding.tool.setVisibility(View.VISIBLE);
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();



    }
}
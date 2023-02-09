package com.abedkhan.multimedia;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.abedkhan.multimedia.Activityes.ShoppingMainActivity;
import com.abedkhan.multimedia.Fragment.AddPostFragment;
import com.abedkhan.multimedia.Fragment.AllCetagoryStoryFragment;
import com.abedkhan.multimedia.Fragment.HomeFragment;
import com.abedkhan.multimedia.Fragment.NotificationFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.SearchFragment;
import com.abedkhan.multimedia.databinding.ActivityMainBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        meowBottomNavigation=findViewById(R.id.meowBottomNavigation);


        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home3));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.add1));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.notification));

        meowBottomNavigation.show(1,true);


        replace(new AllCetagoryStoryFragment());


        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {


                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new AddPostFragment());
                        break;

                    case 3:
                        replace(new NotificationFragment());

                        break;
                }

                return null;
            }
        });



binding.userProfile.setOnClickListener(view -> {
    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ProfileFragment()).commit();
});


binding.searchBar.setOnClickListener(view -> {
    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SearchFragment()).commit();
});


binding.shopping.setOnClickListener(view -> {
    startActivity(new Intent(getApplicationContext(),ShoppingMainActivity.class));
});





    }
    private void replace(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Fragment PersonFragment=new Fragment();
//
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame,PersonFragment).commit();
//
//            }
//        });



    }
}
package com.abedkhan.multimedia;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.abedkhan.multimedia.Fragment.AddPostFragment;
import com.abedkhan.multimedia.Fragment.AllCetagoryStoryFragment;
import com.abedkhan.multimedia.Fragment.HomeFragment;
import com.abedkhan.multimedia.Fragment.SearchFragment;
import com.abedkhan.multimedia.Fragment.ShoppingFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
MeowBottomNavigation meowBottomNavigation;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        meowBottomNavigation=findViewById(R.id.meowBottomNavigation);



//meowBottomNavigation.setCount(1,"10");
//
////
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home3));
//        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.search));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.add1));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.story));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.shop3));
        meowBottomNavigation.show(1,true);


        replace(new AllCetagoryStoryFragment());


        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {


                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;

//                    case 2:
//                        replace(new SearchFragment());
//                        break;

                    case 3:
                        replace(new AddPostFragment());

                        break;

                    case 4:
                        replace(new AllCetagoryStoryFragment());

                        break;

                    case 5:
                        replace(new ShoppingFragment());

                        break;
                }

                return null;
            }
        });

//        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                switch (model.getId()){
//
//                    case 1:
//
//                        break;
//                }
//
//                return null;
//            }
//        });
//
//        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                switch (model.getId()){
//
//                    case 2:
//
//                        break;
//                }
//
//                return null;
//            }
//        });
//
//        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//
//                switch (model.getId()){
//
//                    case 3:
//
//                        break;
//                }
//
//                return null;
//            }
//        });







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
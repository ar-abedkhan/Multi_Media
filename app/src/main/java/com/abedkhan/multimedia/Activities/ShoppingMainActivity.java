package com.abedkhan.multimedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abedkhan.multimedia.Fragment.AddPostFragment;
import com.abedkhan.multimedia.Fragment.HomeFragment;
import com.abedkhan.multimedia.Fragment.NotificationFragment;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.ActivityShoppingMainBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ShoppingMainActivity extends AppCompatActivity {
ActivityShoppingMainBinding binding;
MeowBottomNavigation meowBottomNavigation;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
//TODO: Here is the shopping activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        databaseReference=FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        bottomMeowMenu();







    }



    private void bottomMeowMenu() {

        meowBottomNavigation=findViewById(R.id.meowBottomNavigation);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.shopping));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.add1));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.notification));

        meowBottomNavigation.show(1,true);



    }









    //for showing user online.......
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
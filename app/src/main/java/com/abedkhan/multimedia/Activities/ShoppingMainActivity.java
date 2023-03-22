package com.abedkhan.multimedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abedkhan.multimedia.Adapters.ShoppingCetagoryAdapter;
import com.abedkhan.multimedia.Fragment.AddPostFragment;
import com.abedkhan.multimedia.Fragment.HomeFragment;
import com.abedkhan.multimedia.Fragment.NotificationFragment;
import com.abedkhan.multimedia.Model.ProductModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.ActivityShoppingMainBinding;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ShoppingMainActivity extends AppCompatActivity {
ActivityShoppingMainBinding binding;
MeowBottomNavigation meowBottomNavigation;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ArrayList<SlideModel> imageList;
    List<ProductModel> cetagories;


//TODO: Here is the shopping activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());







        imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.travel1,"Garden", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.food1,"All Food", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.cheamistry,"Night", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.food2,"Burger", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.b1,"Mars", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.food3,"Roll", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.travel1,"Nature", ScaleTypes.CENTER_CROP));

        binding.imageSlider.setImageList(imageList);


        cetagories=new ArrayList<>();

        cetagories.add(new ProductModel("Garden",R.drawable.img1));
        cetagories.add(new ProductModel("All Food",R.drawable.travel1));
        cetagories.add(new ProductModel("Night",R.drawable.cheamistry));
        cetagories.add(new ProductModel("Burger",R.drawable.food1));
        cetagories.add(new ProductModel("Mars",R.drawable.b1));
        cetagories.add(new ProductModel("Roll",R.drawable.food2));
        cetagories.add(new ProductModel("Nature",R.drawable.food3));

        ShoppingCetagoryAdapter adapter=new ShoppingCetagoryAdapter(this,cetagories);
        binding.hotOfferRecycler.setAdapter(adapter);






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
package com.abedkhan.multimedia.Activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.abedkhan.multimedia.databinding.ActivityShoppingMainBinding;

public class ShoppingMainActivity extends AppCompatActivity {
ActivityShoppingMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShoppingMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());











    }
}
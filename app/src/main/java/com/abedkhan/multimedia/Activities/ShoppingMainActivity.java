package com.abedkhan.multimedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.abedkhan.multimedia.databinding.ActivityShoppingMainBinding;

public class ShoppingMainActivity extends AppCompatActivity {
ActivityShoppingMainBinding binding;

//TODO: Here is the shopping activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.i("TAG", "Shopping Main activity ");











    }
}
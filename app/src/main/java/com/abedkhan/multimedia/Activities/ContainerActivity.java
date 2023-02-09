package com.abedkhan.multimedia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.SearchFragment;
import com.abedkhan.multimedia.Fragment.SignUpFragmentOne;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.ActivityContainerBinding;

public class ContainerActivity extends AppCompatActivity {

    ActivityContainerBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();

        if (intent.getBooleanExtra("isProfileCLicked", false)){
            replace(new ProfileFragment());
        } else if (intent.getBooleanExtra("isSearchClicked",false)) {
            replace(new SearchFragment());
        }else if (intent.getBooleanExtra("reg",false)){
            replace(new SignUpFragmentOne());
        }

    }

    private void replace(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, fragment).commit();
    }
}
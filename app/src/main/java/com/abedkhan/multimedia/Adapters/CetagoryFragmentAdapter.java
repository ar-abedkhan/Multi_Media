package com.abedkhan.multimedia.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abedkhan.multimedia.Fragment.AddPostFragment;
import com.abedkhan.multimedia.Fragment.HomeSubContainerFragment;
import com.abedkhan.multimedia.Fragment.NotificationFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.ReadStoryFragment;

//TODO: vai ekhane theke cetagory tolbar kj kore.....niche swich case e fragment set korsi demo egula...

public class CetagoryFragmentAdapter extends FragmentPagerAdapter {
    String [] categoryName ={"All","Category","Popular Post","Popular Writer"};

    public CetagoryFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (position){
            case 0:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 0);
                break;
            case 1:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 1);
                break;
            case 2:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 2);
                break;
            case 3:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 3);
                break;

            case 4:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 4);
                break;
        }
//        fragment =  new HomeSubContainerFragment();

//        if (fragment != null) {
////            Bundle args = new Bundle();
//            args.putInt("position", position);
//            fragment.setArguments(args);
//        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return categoryName.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryName[position];
    }
}

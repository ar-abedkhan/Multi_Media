package com.abedkhan.multimedia.Adapters;

import android.os.Bundle;
import android.util.Log;

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
import com.abedkhan.multimedia.Fragment.ShowPostByCategoryFragment;

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
                Log.i("tag", "position 0: "+0);
                break;
            case 1:
//                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 1);
//                Log.i("tag", "position 1: "+1);
                fragment = new ShowPostByCategoryFragment();

                break;
            case 2:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 2);
                Log.i("tag", "position 2: "+2);

                break;
            case 3:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 3);
                Log.i("tag", "position 3: "+3);

                break;

            case 4:
                fragment =  new HomeSubContainerFragment();
                args.putInt("position", 4);
                Log.i("tag", "position 4: "+4);

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

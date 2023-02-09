package com.abedkhan.multimedia.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abedkhan.multimedia.Fragment.NotificationFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.ReadStoryFragment;

public class CetagoryFragmentAdapter extends FragmentPagerAdapter {
    String [] cetagoryName ={"All","Category","Popular Post","Popular Writer"};

    public CetagoryFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProfileFragment();
            case 1:
                return new NotificationFragment();
            case 2:
                return new ReadStoryFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return cetagoryName.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return cetagoryName[position];
    }
}

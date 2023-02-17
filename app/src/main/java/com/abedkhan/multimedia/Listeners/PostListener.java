package com.abedkhan.multimedia.Listeners;

import androidx.fragment.app.Fragment;

public interface PostListener {
    void gotoFragmentWithValue(Fragment fragment, String userID);
    boolean followButtonClickedEvent(String userID);
}

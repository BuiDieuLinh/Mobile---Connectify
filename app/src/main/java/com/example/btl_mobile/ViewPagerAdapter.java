package com.example.btl_mobile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("ViewPagerAdapter", "Creating fragment at position: " + position);
        switch (position){
            case 0:
                return new FollowsFragment();
            case 1:
                return new FollowingFragment();
            case 2:
                return new FlagFragment();
            default:
                return new FollowsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

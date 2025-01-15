package com.example.btl_mobile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterTabProfile extends FragmentStateAdapter {
    public ViewPagerAdapterTabProfile(@NonNull ProfileFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("ViewPagerAdapter", "Creating fragment at position: " + position);
        switch (position){
            case 0:
                return new PostByUserFragment();
            case 1:
                return new VideoByUserFragment();
            case 2:
                return new ImageByUserFragment();
            default:
                return new PostByUserFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

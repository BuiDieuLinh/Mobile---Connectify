package com.example.btl_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use thefactory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapterTabProfile viewPagerAdapter;
    TextView tvFollows, tvFollowing;
    Button btnEdit, btnShare;
    ImageButton ibtnAddFriend;
    LinearLayout linearLayoutExplore;
    ImageView imgMenu, imgNewPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutInProfile);
        viewPager2 = view.findViewById(R.id.viewpagerProfile);
        viewPagerAdapter = new ViewPagerAdapterTabProfile(this);
        viewPager2.setAdapter(viewPagerAdapter);
        // Bắt đầu lại việc thiết lập TabLayoutMediator
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.grid);
                    break;
                case 1:
                    tab.setIcon(R.drawable.screen_play);
                    break;
                case 2:
                    tab.setIcon(R.drawable.user);
                    break;
            }
        });
        tabLayoutMediator.attach();

        tvFollows = view.findViewById(R.id.tvFollows);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        btnEdit = view.findViewById(R.id.btnEditProfile);
        btnShare = view.findViewById(R.id.btnShareProfile);
        ibtnAddFriend = view.findViewById(R.id.ibtnAddFriend);
        linearLayoutExplore = view.findViewById(R.id.linearLayoutExplore);
        imgMenu = view.findViewById(R.id.imgMenu);
        imgNewPost = view.findViewById(R.id.imgNewPost);

        tvFollows.setOnClickListener(v-> {
            Intent it  = new Intent(getActivity(), actFollows.class);
            startActivity(it);
        });
        tvFollowing.setOnClickListener(v -> {
            Intent it = new Intent(getActivity(), actFollows.class);
            startActivity(it);
        });
        btnEdit.setOnClickListener(v -> {
            Intent it = new Intent(getActivity(), actEditProfile.class);
            startActivity(it);
        });

        btnShare.setOnClickListener(v-> {

        });
        ibtnAddFriend.setOnClickListener(v -> {
            if (linearLayoutExplore.getVisibility() == View.GONE) {
                // Hiển thị LinearLayout
                linearLayoutExplore.setVisibility(View.VISIBLE);
            } else {
                // Ẩn LinearLayout
                linearLayoutExplore.setVisibility(View.GONE);
            }
        });
        imgMenu.setOnClickListener(v -> {
            Intent it = new Intent(getActivity(), actSetting.class);
            startActivity(it);
        });
        return view;
    }
}
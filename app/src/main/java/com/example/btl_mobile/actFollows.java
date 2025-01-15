package com.example.btl_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class actFollows extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follows);

        imgBack = findViewById(R.id.imgBack);
        tabLayout = findViewById(R.id.tabFollowContainer);
        viewPager2 = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        // Bắt đầu lại việc thiết lập TabLayoutMediator
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Người theo dõi");
                    break;
                case 1:
                    tab.setText("Đang theo dõi");
                    break;
                case 2:
                    tab.setText("Bị gắn cờ");
                    break;
            }
        });
        tabLayoutMediator.attach();

        imgBack.setOnClickListener(v -> {
            Intent it = new Intent(this, ProfileFragment.class);
            startActivity(it);
        });
    }
}

package com.example.btl_mobile;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


public class actProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        addControl();
    }

    private void addControl() {
//        tabManage.setup();
//        TabHost.TabSpec spec1, spec2, spec3;
//        // tabposts
//        spec1 = tabManage.newTabSpec("tabPosts");
//        spec1.setContent(R.id.tab1);
//        spec1.setIndicator("", getResources().getDrawable(R.drawable.grid));
//        tabManage.addTab(spec1);
//        // tabvideoshort
//        spec2 = tabManage.newTabSpec("tabVideoshort");
//        spec2.setContent(R.id.tab2);
//        spec2.setIndicator("", getResources().getDrawable(R.drawable.videoshort));
//        tabManage.addTab(spec2);
//        // tabimage
//        spec3 = tabManage.newTabSpec("tabImage");
//        spec3.setContent(R.id.tab3);
//        spec3.setIndicator("", getResources().getDrawable(R.drawable.portrait));
//        tabManage.addTab(spec3);
    }
}

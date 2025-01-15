package com.example.btl_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class actManage extends AppCompatActivity {
    Button btnManageUser, btnManagePost, btnManageAd, btnSetting, btnHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeadmin);
        btnManageUser = findViewById(R.id.btnManageUser);
        btnManagePost = findViewById(R.id.btnManagePost);
        btnManageAd = findViewById(R.id.btnManageAd);
        btnSetting = findViewById(R.id.btnManageSetting);
        btnHelp = findViewById(R.id.btnManageHelper);

        btnManageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(actManage.this, actManageUser.class);
                startActivity(it);
                finish();
            }
        });
        btnManagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(actManage.this, actManagerPost.class);
                startActivity(it);
            }
        });
    }
}

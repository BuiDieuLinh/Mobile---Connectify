package com.example.btl_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class actManageUser extends AppCompatActivity {
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(v -> {
            Intent it = new Intent(this, actManage.class);
            startActivity(it);
            finish(); //
        });
    }
}

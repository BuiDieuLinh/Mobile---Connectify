package com.example.btl_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class actSetting extends AppCompatActivity {

    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v->{
            Intent it = new Intent(this, ProfileFragment.class);
            startActivity(it);
        });
    }
}

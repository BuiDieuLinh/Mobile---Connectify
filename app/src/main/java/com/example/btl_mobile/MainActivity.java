package com.example.btl_mobile; // packagename

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.btl_mobile.R;
import com.example.btl_mobile.actIndex;

// path sd Card /data/data/AppName (packagename) /databases/DBname (ten CSDL. VD test.db)

public class MainActivity extends AppCompatActivity {
    // Bước 1: Khai báo các thành phần giao diện
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeadmin);
//        btnLogin = findViewById(R.id.btnLogin);
//
//        btnLogin.setOnClickListener(v -> {
//            Intent it = new Intent(MainActivity.this, actIndex.class);
//            startActivity(it);
//            finish(); //
//        });
    }
}

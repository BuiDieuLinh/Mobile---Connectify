package com.example.btl_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Bước 1: Khai báo các thành phần giao diện
    Button btnLogin;
    EditText edtUserName, edtPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        btnLogin = findViewById(R.id.btnLogin);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);

        // Bước 2: Đặt sự kiện cho nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUserName.getText().toString();
                String passwordHash = edtPassWord.getText().toString();

                // Gọi hàm đăng nhập
                loginUser(username, passwordHash);
            }
        });
    }

    // Bước 3: Hàm đăng nhập
    private void loginUser(String username, String passwordHash) {
        // Tạo đối tượng Login
        Login login = new Login(username, passwordHash);

        // Gửi yêu cầu tới API qua Retrofit
        ApiService apiService = RetrofitClient.getApiService();
        Call<Login> call = apiService.Login(login);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Login login = response.body();
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    // Lưu thông tin username và password vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", login.getUsername());
                    editor.putString("password", login.getPasswordHash());
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, actTabControl.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.btl_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class actIndex extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indexconnecttify);

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");  // Default là chuỗi rỗng nếu không tìm thấy
        String password = sharedPreferences.getString("password", "");  // Default là chuỗi rỗng nếu không tìm thấy

        //Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

        // Gửi yêu cầu tới API qua Retrofit
        ApiService apiService = RetrofitClient.getApiService();
        Call<User> call = apiService.Get_By_UserName(username);

        // Thực hiện gọi API
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Lấy kết quả trả về từ API
                    User user = response.body();
                    // Xử lý với đối tượng user nhận được từ API
                    Toast.makeText(actIndex.this, "User found: " + user.getUserID(), Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý lỗi nếu không thành công
                    Toast.makeText(actIndex.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                Toast.makeText(actIndex.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

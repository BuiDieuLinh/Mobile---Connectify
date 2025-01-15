package com.example.btl_mobile;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class actMessage extends AppCompatActivity {
    private LinearLayout userListLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatconnectify);

        // Khởi tạo LinearLayout chứa danh sách người dùng
        userListLayout = findViewById(R.id.userListLayout);

        // Gọi API để lấy dữ liệu người dùng
        fetchUsers();
    }

    private void fetchUsers() {
        SharedPreferences sharedPreferences = getSharedPreferences("editor", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1); // Lấy giá trị userID

        if (userID == -1) {
            Toast.makeText(actMessage.this, "userID không tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<User>> call = apiService.Get_Friend_By_UserID(userID);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gọi phương thức để hiển thị dữ liệu trong giao diện
                    populateUserList(response.body());
                } else {
                    Log.e("Error", "Failed to fetch users. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Error", "API call failed: " + t.getMessage());
            }
        });
    }

    private void populateUserList(List<User> userList) {
        // Dọn dẹp dữ liệu cũ trong LinearLayout
        userListLayout.removeAllViews();

        for (User user : userList) {
            // Tạo TextView cho mỗi người dùng
            TextView userTextView = new TextView(this);
            userTextView.setText(user.getUsername()); // Đặt tên người dùng từ API
            userTextView.setPadding(16, 16, 16, 16);
            userTextView.setTextSize(18);
            userTextView.setGravity(Gravity.START);

            // Thêm TextView vào LinearLayout
            userListLayout.addView(userTextView);
        }
    }
}



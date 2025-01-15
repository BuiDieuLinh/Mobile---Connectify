package com.example.btl_mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.net.URL;
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
            // Tạo một LinearLayout cho mỗi người dùng
            LinearLayout userLayout = new LinearLayout(this);
            userLayout.setOrientation(LinearLayout.HORIZONTAL);
            userLayout.setPadding(16, 16, 16, 16);

            // Tạo FrameLayout để chứa ảnh và border
            FrameLayout avatarFrameLayout = new FrameLayout(this);
            avatarFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
            avatarFrameLayout.setBackgroundResource(R.drawable.border_gradient); // Áp dụng drawable border

            // Tạo CardView để chứa ImageView (bo tròn)
            CardView cardView = new CardView(this);
            cardView.setLayoutParams(new FrameLayout.LayoutParams(90, 90));
            cardView.setCardBackgroundColor(Color.WHITE);
            cardView.setRadius(45); // Bo tròn ảnh
            cardView.setContentPadding(2, 2, 2, 2);

            // Tạo ImageView để hiển thị ảnh đại diện
            ImageView avatarImageView = new ImageView(this);
            avatarImageView.setLayoutParams(new CardView.LayoutParams(90, 90));
            avatarImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Tải ảnh từ URL (không dùng Glide)
            Thread thread = new Thread(() -> {
                try {
                    URL url = new URL(user.getProfilePictureURL());
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    // Đặt ảnh lên ImageView trên UI thread
                    runOnUiThread(() -> avatarImageView.setImageBitmap(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hiển thị ảnh mặc định nếu URL không hợp lệ hoặc tải thất bại
                    runOnUiThread(() -> avatarImageView.setImageResource(R.drawable.lvm));
                }
            });
            thread.start();

            // Thêm ImageView vào CardView
            cardView.addView(avatarImageView);

            // Thêm CardView vào FrameLayout (bao bọc ảnh)
            avatarFrameLayout.addView(cardView);

            // Tạo LinearLayout để chứa thông tin văn bản
            LinearLayout textLayout = new LinearLayout(this);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setPadding(16, 0, 0, 0);

            // Tạo TextView để hiển thị tên đầy đủ
            TextView userTextView = new TextView(this);
            userTextView.setText(user.getFullName());
            userTextView.setTextSize(18);
            userTextView.setTextColor(Color.BLACK);

            // Tạo TextView để hiển thị tên người dùng
            TextView userNameView = new TextView(this);
            userNameView.setText(user.getUsername());
            userNameView.setTextSize(14);
            userNameView.setTextColor(Color.GRAY);

            // Thêm các TextView vào textLayout
            textLayout.addView(userTextView);
            textLayout.addView(userNameView);

            // Thêm FrameLayout (ảnh) và textLayout vào userLayout
            userLayout.addView(avatarFrameLayout);
            userLayout.addView(textLayout);

            // Lưu userID vào tag của userLayout để lấy khi click
            userLayout.setTag(user.getUserID());

            // Thêm sự kiện click vào userLayout
            userLayout.setOnClickListener(v -> {
                // Lấy userID từ tag của userLayout
                int userID = (int) v.getTag();

                // Lưu thông tin người dùng vào SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("userClick", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", user.getUsername());
                editor.putString("fullName", user.getFullName());
                editor.putInt("userID", userID); // Lưu userID
                editor.apply();

                // Chuyển sang actMessages
                Intent intent = new Intent(this, actMessages.class);
                startActivity(intent);
            });

            // Thêm userLayout vào userListLayout
            userListLayout.addView(userLayout);
        }
    }





}



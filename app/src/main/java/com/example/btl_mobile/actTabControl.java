package com.example.btl_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class actTabControl extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapterTabControl viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabcontrol);

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");  // Default là chuỗi rỗng nếu không tìm thấy
        String password = sharedPreferences.getString("password", "");  // Default là chuỗi rỗng nếu không tìm thấy

        // Gửi yêu cầu tới API qua Retrofit
        ApiService apiService = RetrofitClient.getApiService();
        Call<User> call = apiService.Get_By_UserName(username);

        // Thực hiện gọi API
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy kết quả trả về từ API
                    User user = response.body();

                    // Lưu thông tin vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("editor", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userID", user.getUserID());
                    editor.apply(); // Hoặc sử dụng editor.commit() nếu muốn lưu đồng bộ


                    // Hiển thị thông báo thành công
                    //Toast.makeText(actTabControl.this, "Thông tin đã được lưu!", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý lỗi nếu không thành công
                    Toast.makeText(actTabControl.this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                Toast.makeText(actTabControl.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập TabLayout và ViewPager
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapterTabControl(this);
        viewPager2.setAdapter(viewPagerAdapter);

        // Thiết lập TabLayoutMediator
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.home);
                    break;
                case 1:
                    tab.setIcon(R.drawable.search);
                    break;
                case 2:
                    tab.setIcon(R.drawable.newpost);
                    break;
                case 3:
                    tab.setIcon(R.drawable.film);
                    break;
                case 4:
                    tab.setIcon(R.drawable.user);
                    break;
            }
        });
        tabLayoutMediator.attach();
    }

}

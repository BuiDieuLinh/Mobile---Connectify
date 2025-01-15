package com.example.btl_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Integer idU = 1;

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<User>> call = apiService.Get_Friend_By_UserID(idU);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Hiển thị thông báo khi lấy dữ liệu thành công
                    Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    // Phương thức hiển thị danh sách người dùng (bạn cần thêm giao diện ở đây)
    private void populateUserList(List<User> userList) {
        // Logic để hiển thị danh sách người dùng lên giao diện
        // Có thể sử dụng RecyclerView hoặc ListView để hiển thị dữ liệu
    }
}

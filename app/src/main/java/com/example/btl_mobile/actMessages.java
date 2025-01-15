package com.example.btl_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class actMessages extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ScrollView scrollView;

    EditText edtMessages;
    ImageButton btnSendMessages;

    // Khai báo ApiService từ RetrofitClient
    ApiService apiService;

    // Khai báo Handler để xử lý tự động lấy tin nhắn mỗi 20 giây
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagesconnect);

        // Khởi tạo các thành phần giao diện
        scrollView = findViewById(R.id.scrollContent);
        linearLayout = findViewById(R.id.linearContent);
        edtMessages = findViewById(R.id.edtMessage);
        btnSendMessages = findViewById(R.id.btnSendMessage);

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userClick", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);  // Lấy userID

        SharedPreferences myInfo = getSharedPreferences("editor", MODE_PRIVATE);
        int myID = myInfo.getInt("userID", -1); // Lấy giá trị userID

        // Kiểm tra nếu không tìm thấy thông tin người dùng
        if (userID == -1) {
            Toast.makeText(actMessages.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        // Lấy ApiService từ RetrofitClient
        apiService = RetrofitClient.getApiService();

        // Nút gửi tin nhắn
        btnSendMessages.setOnClickListener(v -> {
            String content = edtMessages.getText().toString().trim();
            if (!content.isEmpty()) {
                // Lấy userID từ SharedPreferences
                SharedPreferences shareFriendInfo = getSharedPreferences("editor", MODE_PRIVATE);
                int friendID = shareFriendInfo.getInt("userID", -1); // Lấy giá trị userID của bạn bè

                if (friendID == -1) {
                    Toast.makeText(actMessages.this, "Không tìm thấy userID!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int senderId = userID; // ID của người gửi
                int receiverID = friendID;   // ID của người nhận

                // Tạo đối tượng Message_Send để gửi tin nhắn
                Message_Send messageSend = new Message_Send(senderId, receiverID, content);

                // Gọi API gửi tin nhắn
                apiService.sendMessage(messageSend).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful()) {
                            // Hiển thị tin nhắn trên giao diện
                            addMessageToView(content, senderId);
                            edtMessages.setText(""); // Xóa nội dung EditText sau khi gửi
                            scrollToBottom(); // Cuộn xuống dưới khi có tin nhắn mới
                        } else {
                            Toast.makeText(actMessages.this, "Gửi tin nhắn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(actMessages.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(actMessages.this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show();
            }
        });

        // Lấy danh sách tin nhắn từ API ngay khi mở ứng dụng
        loadMessages(userID, myID);

        // Tạo Runnable để tự động gọi API mỗi 20 giây
        runnable = new Runnable() {
            @Override
            public void run() {
                loadMessages(userID, myID);  // Gọi lại API để lấy tin nhắn mới
                handler.postDelayed(this, 10000);  // Lập lại sau 20 giây
            }
        };

        // Bắt đầu gọi API sau khi mở ứng dụng
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dừng Handler khi Activity bị hủy
        handler.removeCallbacks(runnable);
    }

    // Phương thức lấy danh sách tin nhắn từ API
    private void loadMessages(int userID, int myID) {
        apiService.getMessageBoxChat(userID, myID).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    linearLayout.removeAllViews();  // Xóa tất cả các tin nhắn cũ
                    for (Message message : messages) {
                        addMessageToView(message.getContent(), message.getSenderId());
                    }
                } else {
                    Toast.makeText(actMessages.this, "Lỗi lấy tin nhắn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(actMessages.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức thêm tin nhắn vào giao diện
    private void addMessageToView(String messageContent, int senderId) {
        // Lấy userID từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userClick", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);  // Lấy userID từ SharedPreferences

        if (userID == -1) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo TextView để hiển thị tin nhắn
        TextView textView = new TextView(this);
        textView.setPadding(20, 20, 20, 20);
        textView.setText(messageContent);

        // Định dạng bố cục cho TextView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);

        // Xác định vị trí hiển thị tin nhắn
        if (senderId == userID) {
            // Nếu là tin nhắn của người dùng thì căn phải
            textView.setBackgroundResource(R.drawable.border_search); // Background cho tin nhắn gửi
            params.gravity = Gravity.END; // Căn phải
        } else {
            // Nếu là tin nhắn của người khác thì căn trái
            textView.setBackgroundResource(R.drawable.border_search); // Background cho tin nhắn nhận
            params.gravity = Gravity.START; // Căn trái
        }

        // Áp dụng các thuộc tính định dạng
        textView.setLayoutParams(params);

        // Thêm TextView vào giao diện
        linearLayout.addView(textView);
    }

    // Phương thức tự động cuộn xuống dưới cùng
    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}

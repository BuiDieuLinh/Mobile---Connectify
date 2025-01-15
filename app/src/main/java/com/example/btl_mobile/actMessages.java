package com.example.btl_mobile;

import android.content.SharedPreferences;
import android.os.Bundle;
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
        String userName = sharedPreferences.getString("userName", "");  // Lấy tên người dùng
        String fullName = sharedPreferences.getString("fullName", "");  // Lấy tên đầy đủ
        int userID = sharedPreferences.getInt("userID", -1);  // Lấy userID

        // Kiểm tra nếu userID hợp lệ
        if (userID != -1) {
            // Hiển thị thông tin người dùng nếu cần
            Toast.makeText(actMessages.this, "UserID: " + userID + " - UserName: " + userName + " - FullName: " + fullName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(actMessages.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        // Nút gửi tin nhắn
        // Nút gửi tin nhắn
        btnSendMessages.setOnClickListener(v -> {
            String content = edtMessages.getText().toString().trim();
            if (!content.isEmpty()) {
                // Lấy userID từ SharedPreferences
                SharedPreferences shareFriendInfo = getSharedPreferences("editor", MODE_PRIVATE);
                int friendID = shareFriendInfo.getInt("userID", -1); // Lấy giá trị userID

                if (friendID == -1) {
                    Toast.makeText(actMessages.this, "Không tìm thấy userID!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int senderId = userID; // ID của người gửi
                int receiverID = 1;   // ID của người nhận (cần thay theo logic)

                // Tạo đối tượng Message
                Message_Send message = new Message_Send(senderId, receiverID, content);

                // Gửi yêu cầu tới API qua Retrofit
                ApiService apiService = RetrofitClient.getApiService();
                Call<Message> send = apiService.sendMessage(message);

                send.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Message sentMessage = response.body();

                            // Hiển thị tin nhắn trên giao diện
                            TextView textView = new TextView(actMessages.this);
                            textView.setPadding(20, 20, 20, 20);
                            textView.setText(sentMessage.getContent());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(0, 0, 0, 20);
                            params.gravity = Gravity.END;
                            textView.setBackgroundResource(R.drawable.border_search);
                            textView.setLayoutParams(params);
                            linearLayout.addView(textView);

                            // Cuộn xuống dưới cùng
                            scrollToBottom();

                            // Xóa nội dung EditText sau khi gửi
                            edtMessages.setText("");
                        } else {
                            Toast.makeText(actMessages.this, "Gửi tin nhắn thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(actMessages.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(actMessages.this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show();
            }
        });


        // Tải tin nhắn từ API
        loadMessages();
    }

    // Phương thức để tải tin nhắn từ API
    private void loadMessages() {

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");  // Default là chuỗi rỗng nếu không tìm thấy
        String password = sharedPreferences.getString("password", "");  // Default là chuỗi rỗng nếu không tìm thấy

        int senderId = 1; // Thay giá trị phù hợp
        int receiverID = 2; // Thay giá trị phù hợp

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Message>> call = apiService.getMessageBoxChat(senderId, receiverID);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Message> messages = response.body();
                    linearLayout.removeAllViews(); // Xóa các tin nhắn cũ

                    for (Message message : messages) {
                        addMessageToView(message, senderId);
                    }

                    // Cuộn xuống dưới cùng khi tải xong tin nhắn
                    scrollToBottom();
                } else {
                    Toast.makeText(actMessages.this, "Chua co tin nhan nao", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(actMessages.this, "Kết nối thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức thêm tin nhắn vào giao diện
    public  int userID = 1;
    private void addMessageToView(Message message, int senderId) {
        // Lấy giá trị userID từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("editor", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1); // Lấy giá trị userID

        // Tạo TextView để hiển thị tin nhắn
        TextView textView = new TextView(this);
        textView.setPadding(20, 20, 20, 20);
        textView.setText(message.getContent());

        // Định dạng bố cục cho TextView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);

        // Xác định vị trí hiển thị tin nhắn
        if (message.getSenderId() == userID) {
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

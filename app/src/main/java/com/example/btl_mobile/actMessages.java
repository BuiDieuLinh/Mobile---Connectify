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

        // Nút gửi tin nhắn
        btnSendMessages.setOnClickListener(v -> {
            String content = edtMessages.getText().toString().trim();
            if (!content.isEmpty()) {
                int senderId = 1; // Thay giá trị phù hợp với logic của bạn
                int receiverID = 2; // Thay giá trị phù hợp với logic của bạn

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

                            // Tạo TextView mới để hiển thị tin nhắn
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
        TextView textView = new TextView(this);
        textView.setPadding(20, 20, 20, 20);
        textView.setText(message.getContent());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);

        // Kiểm tra nếu đây là tin nhắn của người gửi thì căn phải
        if (message.getSenderId() == senderId) {
            textView.setBackgroundResource(R.drawable.border_search); // Background tùy chỉnh
            params.gravity = Gravity.END; // Căn phải cho tin nhắn của người gửi
        } else {
            textView.setBackgroundResource(R.drawable.border_search);
            params.gravity = Gravity.START; // Căn trái cho tin nhắn của người nhận
        }

        textView.setLayoutParams(params);
        linearLayout.addView(textView);
    }


    // Phương thức tự động cuộn xuống dưới cùng
    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}

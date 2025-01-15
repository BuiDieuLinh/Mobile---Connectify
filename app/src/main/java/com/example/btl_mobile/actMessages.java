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

import com.microsoft.signalr.HubConnection;

public class actMessages extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ScrollView scrollView;

    EditText edtMessages;
    ImageButton btnSendMessages;

    // Tạo một đối tượng SignalRClient
    SignalRClient signalRClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagesconnect);

        // Khởi tạo các thành phần giao diện
        scrollView = findViewById(R.id.scrollContent);
        linearLayout = findViewById(R.id.linearContent);
        edtMessages = findViewById(R.id.edtMessage);
        btnSendMessages = findViewById(R.id.btnSendMessage);

        // Tạo và kết nối SignalRClient
        signalRClient = new SignalRClient();
        signalRClient.startConnection();

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userClick", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);  // Lấy userID

        if (userID == -1) {
            Toast.makeText(actMessages.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

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
                int receiverID = friendID;   // ID của người nhận

                // Gửi tin nhắn qua SignalR
                signalRClient.sendMessage(senderId, receiverID, content);

                // Hiển thị tin nhắn trên giao diện
                addMessageToView(content, senderId);

                // Xóa nội dung EditText sau khi gửi
                edtMessages.setText("");
            } else {
                Toast.makeText(actMessages.this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show();
            }
        });

        // Tải tin nhắn từ SignalR (bạn có thể sử dụng phương thức on để nhận tin nhắn)
        // signalRClient.onMessageReceived(); // Ví dụ nếu muốn nhận tin nhắn
    }

    // Phương thức thêm tin nhắn vào giao diện
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

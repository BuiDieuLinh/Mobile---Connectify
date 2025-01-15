package com.example.btl_mobile;

import android.util.Log;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubException;
import java.util.concurrent.ExecutionException;

public class SignalRClient {
    private HubConnection hubConnection;

    public SignalRClient() {
        // Tạo kết nối với SignalR Hub
        hubConnection = HubConnectionBuilder.create("https://c19e-113-167-153-148.ngrok-free.app/chatHub")
                .build();


        // Đăng ký sự kiện nhận tin nhắn
        hubConnection.on("ReceiveMessage", (senderId, receiverId, content) -> {
            // Đây là nơi bạn xử lý tin nhắn nhận được
            Log.d("SignalR", "Received message: " + content);
        }, String.class, String.class, String.class);
    }

    // Bắt đầu kết nối SignalR
    public void startConnection() {
        // Sử dụng try-catch để xử lý việc bắt đầu kết nối
        try {
            hubConnection.start().blockingAwait(); // Chờ kết nối hoàn tất
            Log.d("SignalR", "Connected to SignalR server.");
        } catch (Exception e) {
            Log.e("SignalR", "Error starting connection: " + e.getMessage());
        }
    }

    // Gửi tin nhắn
    public void sendMessage(Integer senderId, Integer receiverId, String content) {
        hubConnection.send("SendMessage", senderId, receiverId, content);
    }

    // Dừng kết nối SignalR
    public void stopConnection() {
        hubConnection.stop();
    }
}

package com.example.btl_mobile;

import android.util.Log;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubException;
import com.example.btl_mobile.ApiService;
import com.example.btl_mobile.Message;
import com.example.btl_mobile.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignalRClient {
    public HubConnection hubConnection;
    private ApiService apiService;

    // Constructor của SignalRClient
    public SignalRClient() {
        // Khởi tạo HubConnection
        hubConnection = HubConnectionBuilder.create("https://c19e-113-167-153-148.ngrok-free.app/chatHub")
                .build();

        // Đăng ký sự kiện nhận tin nhắn
        hubConnection.on("ReceiveMessage", (senderId, receiverId, content) -> {
            Log.d("SignalR", "Received message: " + content);
        }, String.class, String.class, String.class);

        // Đăng ký sự kiện nhận tin nhắn cũ
        hubConnection.on("ReceiveOldMessages", (messages) -> {
            Log.d("SignalR", "Received old messages: " + messages);
            displayOldMessages(messages);
        }, List.class);

        // Khởi tạo Retrofit API Service
        apiService = RetrofitClient.getApiService();
    }

    // Bắt đầu kết nối SignalR và gọi API để tải tin nhắn cũ
    public void startConnection(int senderId, int receiverId) {
        try {
            hubConnection.start().blockingAwait();
            Log.d("SignalR", "Connected to SignalR server.");

            // Gọi phương thức GetOldMessages để tải tin nhắn cũ từ API
            getOldMessagesFromApi(senderId, receiverId);

        } catch (Exception e) {
            Log.e("SignalR", "Error starting connection: " + e.getMessage());
        }
    }

    // Gọi API để lấy tin nhắn cũ
    private void getOldMessagesFromApi(int senderId, int receiverId) {
        Call<List<Message>> call = apiService.getMessageBoxChat(senderId, receiverId);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> messages = response.body();
                    // Hiển thị tin nhắn cũ
                    displayOldMessages(messages);
                } else {
                    Log.e("SignalR", "Error fetching old messages: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("SignalR", "Error fetching old messages: " + t.getMessage());
            }
        });
    }

    // Gửi tin nhắn qua SignalR
    public void sendMessage(Integer senderId, Integer receiverId, String content) {
        hubConnection.send("SendMessage", senderId, receiverId, content);
    }

    // Dừng kết nối SignalR
    public void stopConnection() {
        hubConnection.stop();
    }

    // Hiển thị tin nhắn cũ
    private void displayOldMessages(List<Message> messages) {
        for (Message message : messages) {
            Log.d("SignalR", "Old message: " + message.getContent());
            // Thêm tin nhắn vào giao diện của bạn (ví dụ: add vào layout hoặc scroll view)
        }
    }
}

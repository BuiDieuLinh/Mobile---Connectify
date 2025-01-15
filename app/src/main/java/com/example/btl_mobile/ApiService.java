package com.example.btl_mobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("Messages_/GetMessageBoxChatAsync")
    Call<List<Message>> getMessageBoxChat(
            @Query("senderId") int senderId,
            @Query("receiverID") int receiverID
    );
    @POST("Messages_/SendMessage")
    Call<Message> sendMessage(@Body Message_Send message);


    // Nguoi dung
    @POST("User_/Login")
    Call<Login> Login(
            @Body Login login
    );

    @GET("User_/Get_By_UserName")
    Call<User> Get_By_UserName (@Query("Username") String Username);

    @GET("User_/user/friendship")
    Call<List<User>> Get_Friend_By_UserID(@Query("UserID") int UserID);
}

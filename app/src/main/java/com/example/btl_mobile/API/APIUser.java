package com.example.btl_mobile.API;

import com.example.btl_mobile.Data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIUser {
    @GET("User_")
    Call<List<User>> getAll();
    @GET("User_")
    Call<List<User>> getAllUsers();
}

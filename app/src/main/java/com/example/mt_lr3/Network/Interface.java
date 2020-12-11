package com.example.mt_lr3.Network;

import com.example.mt_lr3.Objects.Users;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Interface {
    @GET("users/")
    public Call<Users> getAllUsers();
}

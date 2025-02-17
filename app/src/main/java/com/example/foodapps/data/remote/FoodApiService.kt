package com.example.foodapps.data.remote

import com.example.foodapps.data.remote.model.LoginRequest
import com.example.foodapps.data.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FoodApiService {
    @FormUrlEncoded
    @POST("user_login_api.php")
    fun getLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
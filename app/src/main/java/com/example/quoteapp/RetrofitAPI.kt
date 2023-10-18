package com.example.quoteapp

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("random")
    fun getDate(): Call<Data>
}
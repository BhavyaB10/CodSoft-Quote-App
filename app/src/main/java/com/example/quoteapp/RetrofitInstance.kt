package com.example.quoteapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://dummyjson.com/quotes/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiInterface: RetrofitAPI by lazy {
        retrofit.create(RetrofitAPI::class.java)
    }
}
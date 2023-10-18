package com.example.quoteapp

import com.google.gson.annotations.SerializedName
data class Data(

    @SerializedName("quote")
    val quote: String,

  @SerializedName("author")
    val author:String
    )
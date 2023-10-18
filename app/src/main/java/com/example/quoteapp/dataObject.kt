package com.example.quoteapp

object dataObject {
    var list = mutableListOf<Data>()

    fun setData(quote:String,author:String){
        list.add(Data(quote,author))
    }
    fun getAllData(): List<Data> {
       return list
    }
}
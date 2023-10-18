package com.example.quoteapp

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quoteapp.databinding.ActivityFavoriteBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class FavoriteQuotesActivity : AppCompatActivity() {
    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private var favoriteQuoteList: ArrayList<Data?>? = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        favoriteQuoteList = getAllFavoriteMyQuotes()
        println("favoriteQuoteList-----" + favoriteQuoteList)

        if (favoriteQuoteList != null) {
            favoriteBinding.recylerView.visibility = View.VISIBLE
            favoriteBinding.tvMessage.visibility = View.GONE
            favoriteBinding.recylerView.adapter = QuotesAdapter(favoriteQuoteList!!)
            favoriteBinding.recylerView.layoutManager = LinearLayoutManager(this)
        } else {
            favoriteBinding.recylerView.visibility = View.GONE
            favoriteBinding.tvMessage.visibility = View.VISIBLE

        }

    }

    private fun getAllFavoriteMyQuotes(): ArrayList<Data?>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json = prefs.getString("MyQuotes", null)
        val type: Type = object : TypeToken<ArrayList<Data?>?>() {}.type
        return gson.fromJson<ArrayList<Data?>>(json, type)
    }


}
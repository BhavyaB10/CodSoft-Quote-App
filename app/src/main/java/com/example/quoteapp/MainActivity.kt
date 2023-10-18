package com.example.quoteapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quoteapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var favoriteQuoteList: ArrayList<Data?>? = ArrayList()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getData()

        if (getAllFavoriteMyQuotes() != null) {
            favoriteQuoteList = getAllFavoriteMyQuotes()
        }
        onClick()


    }

    private fun onClick() {

        binding.floatingActionButtonRefresh.setOnClickListener {
            getData()
        }

        binding.floatingActionButton.setOnClickListener {

            val text = binding.quote.text.toString()
             val intent = Intent()
             intent.action = Intent.ACTION_SEND
             intent.type = "text/plain"
             intent.putExtra(Intent.EXTRA_TEXT, text)
             startActivity(Intent.createChooser(intent, "Share Via"))


        }
        binding.favourite.setOnClickListener {


            val quote = binding.quote.text.toString()
            val author = binding.quoteAuthor.text.toString()


            if (quote.trim { it <= ' ' }.isNotEmpty() && author.trim { it <= ' ' }.isNotEmpty()) {
                val quote = binding.quote.text.toString()
                val author = binding.quoteAuthor.text.toString()

                var quoteList: ArrayList<Data> = ArrayList()
                quoteList.add(Data(quote, author))

                println("dataList-----$quoteList")


                favoriteQuoteList?.addAll(quoteList)

                println("favoriteQuoteList11111-----$favoriteQuoteList")

                addToFavorite(favoriteQuoteList)
                Toast.makeText(
                    applicationContext,
                    "Quote is saved in favourite",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }
        binding.buttonFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteQuotesActivity::class.java)
            startActivity(intent)

        }
    }


    private fun getData() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait Quotes are loading.")
        progressDialog.show()

        RetrofitInstance.apiInterface.getDate().enqueue(object : Callback<Data?> {
            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
                val data: Data? = response.body()
                binding.quote.text = "❝ " + data!!.quote + " ❞"
                binding.quoteAuthor.text = " ~ " + data!!.author
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<Data?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }


    private fun addToFavorite(favoriteList: ArrayList<Data?>?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        try {
            val gson = Gson()
            val json = gson.toJson(favoriteList)
            editor.putString("MyQuotes", json)
            editor.commit() // This line is IMPORTANT !!!

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAllFavoriteMyQuotes(): ArrayList<Data?>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json = prefs.getString("MyQuotes", null)
        val type: Type = object : TypeToken<ArrayList<Data?>?>() {}.type
        println("favoriteQuoteList-----$favoriteQuoteList")
        return gson.fromJson<ArrayList<Data?>>(json, type)
    }

}
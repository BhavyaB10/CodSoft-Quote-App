package com.example.quoteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuotesAdapter(private var data: ArrayList<Data?>) :
    RecyclerView.Adapter<QuotesAdapter.viewHolder>() {


    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val quote: TextView = itemView.findViewById(R.id.quote)
        val author: TextView = itemView.findViewById(R.id.quoteAuthor)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return viewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return data.size

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.quote.text = data[position]!!.quote
        holder.author.text = data[position]!!.author
    }
}
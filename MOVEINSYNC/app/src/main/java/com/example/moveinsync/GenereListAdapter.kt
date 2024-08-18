package com.example.moveinsync

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GenereListAdapter(val context: Context, val genres: List<String>) :
    RecyclerView.Adapter<GenereListAdapter.GenereViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenereViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false)
        return GenereViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenereViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    class GenereViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val genreTextView: TextView = view.findViewById(R.id.genre_text_view)
        fun bind(genre: String) {
            genreTextView.text = genre

        }
    }
}

package com.example.moveinsync

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DropdownAdapter(private val items: List<String>,  val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<DropdownAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
        fun bind(genre: String) {
            itemView.findViewById<TextView>(android.R.id.text1).text = genre
            itemView.setOnClickListener {
                onItemClick(genre) // Invoke the callback with the clicked genre
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}



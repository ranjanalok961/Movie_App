package com.example.moveinsync

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecycleAdapter(
    var mtx: Context,
    var list: ArrayList<Movie>,
    val parentFragmentManager: FragmentManager
) : RecyclerView.Adapter<RecycleAdapter.MyHolder>() {
    class MyHolder(var view : View) : RecyclerView.ViewHolder(view){
        var name = view.findViewById<TextView>(R.id.textView2)
        var rating = view.findViewById<TextView>(R.id.rating)
        var image = view.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.MyHolder {
        val view = LayoutInflater.from(mtx).inflate(R.layout.itemcard,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleAdapter.MyHolder, position: Int) {
        var item = list[position]
        holder.name.text = item.title
        holder.rating.text = item.imdbRating.toString()
        // Load image using Glide
        Glide.with(mtx)
            .load(item.posterurl)  // Assuming posterurl is a string URL
            .placeholder(R.drawable.img_1)  // Optional placeholder image// Optional error image
            .into(holder.image)
        holder.itemView.setOnClickListener {
            val fragmentB = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable("movie_key", item)
            fragmentB.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentB)
                .addToBackStack(null)
                .commit()
        }
        }


    override fun getItemCount(): Int {
        return list.size
    }
}
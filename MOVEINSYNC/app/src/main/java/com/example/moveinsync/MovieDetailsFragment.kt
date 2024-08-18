package com.example.moveinsync

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson


@SuppressLint("SetTextI18n")
class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        SetUi(view)
        return view
    }
    fun SetUi(view : View){
        val item = arguments?.getSerializable("movie_key") as? Movie

        val image = view.findViewById<ImageView>(R.id.posterUrl)
        val title = view.findViewById<TextView>(R.id.title)
        val timing = view.findViewById<TextView>(R.id.timing)
        val rating = view.findViewById<TextView>(R.id.rating)
        val releaseDate = view.findViewById<TextView>(R.id.releaseDateText)
        val description = view.findViewById<TextView>(R.id.descreption)
        val recycle = view.findViewById<RecyclerView>(R.id.genreList)
        recycle.hasFixedSize()
        recycle.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        if (item != null) {
            recycle.adapter = GenereListAdapter(view.context,item.genres)
        }
        val castRecycle = view.findViewById<RecyclerView>(R.id.castList)
        castRecycle.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        if (item != null) {
            castRecycle.adapter = GenereListAdapter(view.context,item.actors)
        }

        Glide.with(view.context)
            .load(item?.posterurl)  // Assuming posterurl is a string URL
            .placeholder(R.drawable.img)  // Optional placeholder image
            .error(R.drawable.baseline_error_24)  // Optional error image
            .into(image)

        title.text = item?.title
        timing.text = item?.duration
        rating.text = item?.imdbRating.toString()
        releaseDate.text = item?.releaseDate
        description.text = item?.storyline

    }
}

package com.example.moveinsync

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var horizontalrecyler : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val button = view.findViewById<ImageButton>(R.id.dropdown_menu)
        searchBar = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById(R.id.recycleView)
        horizontalrecyler = view.findViewById(R.id.horizontalRecycleView)
        button.setOnClickListener {
            showDropdownMenu(view)
        }

        setAdapter(view)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterMovies(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        horizontalRecycleView(view)
        return view
    }

    fun horizontalRecycleView(view: View?) {
        horizontalrecyler.setHasFixedSize(true)
        // Set layout manager to horizontal orientation
        horizontalrecyler.layoutManager = LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                val sortedMovies = ArrayList(movies.sortedByDescending { it.imdbRating })
                horizontalrecyler.adapter = view?.let { RecycleAdapter(it.context, sortedMovies, parentFragmentManager) }
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    private fun showDropdownMenu(view: View) {
        val dialog = view.context.let { Dialog(it) }
        val inflater = LayoutInflater.from(view.context)
        val dialogView = inflater.inflate(R.layout.dropdown_menu, null)
        dialog.setContentView(dialogView)

        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)

        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recycler_view_dropdown)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                var selectedGenre = ""
                val uniqueGenres = extractUniqueGenres(movies)
                recyclerView.adapter = DropdownAdapter(uniqueGenres) { selected ->
                    selectedGenre = selected
                    movieViewModel.filterMoviesByGenre(selectedGenre)
                    dialog.dismiss() // Dismiss the dialog after selection
                }
            }
        })

        dialogView.findViewById<Button>(R.id.stack_button).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setAdapter(view: View) {
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(view.context, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = gridLayoutManager

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        movieViewModel.filteredMovies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                recyclerView.adapter = RecycleAdapter(view.context, movies as ArrayList<Movie>, parentFragmentManager)
            }
        })
    }

    private fun filterMovies(query: String) {
        movieViewModel.filteredMovies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                val filteredMovies = movies.filter { it.title.contains(query, ignoreCase = true) }
                recyclerView.adapter = RecycleAdapter(requireContext(), filteredMovies as ArrayList<Movie>, parentFragmentManager)
            }
        })
    }

    private fun extractUniqueGenres(movies: List<Movie>): List<String> {
        val genreSet = mutableSetOf<String>()
        genreSet.add("All")
        for (movie in movies) {
            genreSet.addAll(movie.genres)
        }
        return genreSet.toList()
    }
}

package com.example.moveinsync

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.moveinsync.MovieRepository.movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        coroutineScope.launch {
            try {
                val movies = RetrofitClient.instance.getMovies()
                Log.d("MovieViewModel", "Movies fetched: $movies")
                MovieRepository.setMovies(movies)
                _filteredMovies.postValue(movies)  // Set the initial filteredMovies to all movies
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching movies", e)
                MovieRepository.setMovies(emptyList())
                _filteredMovies.postValue(emptyList())  // Handle the case where no movies are fetched
            }
        }
    }

    val movies: LiveData<List<Movie>> get() = MovieRepository.movies

    private val _filteredMovies = MutableLiveData<List<Movie>>()
    val filteredMovies: LiveData<List<Movie>> get() = _filteredMovies

    fun filterMoviesByGenre(genre: String) {
        coroutineScope.launch {
            val allMovies = MovieRepository.movies.value ?: emptyList()
            val filtered = if (genre.isEmpty() || genre.equals("All")) allMovies else allMovies.filter { it.genres.contains(genre) }
            _filteredMovies.postValue(filtered)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel() // Cancel coroutines when ViewModel is cleared
    }
}




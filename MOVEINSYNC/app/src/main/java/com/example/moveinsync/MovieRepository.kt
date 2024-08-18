package com.example.moveinsync

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object MovieRepository {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun setMovies(movies: List<Movie>) {
        _movies.postValue(movies)
    }
}



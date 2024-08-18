package com.example.moveinsync

import retrofit2.Call
import retrofit2.http.GET

interface MovieApiService {
    @GET("3c58f63c-b699-4f45-ba6f-befdd397a2b8")
    suspend fun getMovies(): List<Movie>
}

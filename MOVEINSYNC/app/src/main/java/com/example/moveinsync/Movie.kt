package com.example.moveinsync

import java.io.Serializable

data class Movie(
    val title: String,
    val year: String,
    val genres: List<String>,
    val ratings: List<Int>,
    val poster: String,
    val contentRating: String,
    val duration: String,
    val releaseDate: String,
    val averageRating: Float,
    val originalTitle: String,
    val storyline: String,
    val actors: List<String>,
    val imdbRating: Float,
    val posterurl: String
): Serializable

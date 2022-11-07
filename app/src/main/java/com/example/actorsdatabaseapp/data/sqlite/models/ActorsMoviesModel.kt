package com.example.actorsdatabaseapp.data.sqlite.models

data class ActorsMoviesModel(
    val actorId: Int,
    val movieId: Int,
    val actorName: String,
    val movieName: String,
    val imdbRate: Int
)

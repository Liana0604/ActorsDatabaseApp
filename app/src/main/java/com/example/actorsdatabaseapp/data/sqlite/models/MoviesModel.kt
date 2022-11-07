package com.example.actorsdatabaseapp.data.sqlite.models

data class MoviesModel(
    val id: Int? = null,
    val name: String,
    val imdbRate: Int,
    val actorId: Int
)

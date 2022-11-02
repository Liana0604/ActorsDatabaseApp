package com.example.actorsdatabaseapp.modules.sqlite


data class ActorsModel(
    val id: Int = 0,
    val name: String,
    val surname: String,
    val age: Int,
    val movieId: Int
//    val pets : ArrayList<Pet>?,
)

package com.example.actorsdatabaseapp.data.sqlite.models


data class ActorsModel(
    val id: Int = 0,
    val name: String,
    val surname: String,
    val age: Int,
    val pets: ArrayList<Pet>?
)

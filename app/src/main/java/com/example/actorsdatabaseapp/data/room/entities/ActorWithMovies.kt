package com.example.actorsdatabaseapp.data.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ActorWithMovies(
    @Embedded val actor: Actors,
    @Relation(
        parentColumn = "actors_name",
        entityColumn = "actors_name"
    )
    val movies: List<Movies>
)

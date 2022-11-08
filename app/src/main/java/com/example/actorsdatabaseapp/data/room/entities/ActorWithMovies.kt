package com.example.actorsdatabaseapp.data.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ActorWithMovies(
    @Embedded val actor: Actors,
    @Relation(
        parentColumn = "id",
        entityColumn = "actor_id",
    )
    val movies: MutableList<Movies>
)

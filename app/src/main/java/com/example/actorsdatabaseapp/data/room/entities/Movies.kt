package com.example.actorsdatabaseapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val imdbRate: Int,
    val actorName: String
)

package com.example.actorsdatabaseapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors_table")
data class Actors(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val surname: String,
    val age: Int,
    //val pets : ArrayList<Pet>
)

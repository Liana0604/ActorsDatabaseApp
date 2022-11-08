package com.example.actorsdatabaseapp.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movies(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    val name: String,
    val imdbRate: Int,
    @ColumnInfo(name = "actor_id") val actorId: Int
)

package com.example.actorsdatabaseapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.actorsdatabaseapp.data.sqlite.models.Pet
import com.google.gson.Gson

@Entity(tableName = "actors_table")
data class Actors(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val surname: String,
    val age: Int,
    val pets: List<Pet>?
)

class PetsTypConverter {
    @TypeConverter
    fun listToJson(value: List<Pet>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Pet>::class.java).toList()
}

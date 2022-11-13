package com.example.actorsdatabaseapp.data.sqlite.models

import com.google.gson.Gson


data class ActorsModel(
    val id: Int = 0,
    val name: String,
    val surname: String,
    val age: Int,
    val pets: ArrayList<Pet>?
)

class PetTypeConverter {
    companion object {
        fun listToJson(value: List<Pet>?) = Gson().toJson(value)

        fun jsonToList(value: String) =
            Gson().fromJson(value, Array<Pet>::class.java).toMutableList()

    }
}
package com.example.actorsdatabaseapp.modules.sqlite

data class Pet(val name: String, val age: Int, val isSmart: Boolean) {
    var petMutableList = mutableListOf<Pet>()
}

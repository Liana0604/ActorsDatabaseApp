package com.example.actorsdatabaseapp.data.sqlite.entities

object ActorEntity {
    const val TABLE_NAME = "actors"
    const val ID = "id"
    const val NAME = "name"
    const val SURNAME = "surname"
    const val AGE = "age"
    const val PET = "pet"

    const val CREATE_ACTORS_TABLE =
        "CREATE TABLE $TABLE_NAME ($ID  INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT NOT NULL, $SURNAME TEXT NOT NULL, $AGE INTEGER NOT NULL, $PET TEXT)"
    const val DESTROY = "DROP TABLE IF EXISTS $TABLE_NAME"
}
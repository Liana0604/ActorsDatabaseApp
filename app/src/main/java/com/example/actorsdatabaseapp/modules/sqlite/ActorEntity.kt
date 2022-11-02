package com.example.actorsdatabaseapp.modules.sqlite

object ActorEntity {
    const val TABLE_NAME = "actors"
    const val ID = "id"
    const val NAME = "name"
    const val SURNAME = "surname"
    const val AGE = "age"
    const val MOVIE_ID = "movie_id"

    //  const val PET = "pet"

    const val CREATE_ACTORS_TABLE =
        "CREATE TABLE $TABLE_NAME ($ID  INTEGER PRIMARY KEY, $NAME TEXT UNIQUE, $SURNAME TEXT, $AGE INTEGER, $MOVIE_ID INTEGER)"
    const val DESTROY = "DROP TABLE IF EXISTS $TABLE_NAME"

}

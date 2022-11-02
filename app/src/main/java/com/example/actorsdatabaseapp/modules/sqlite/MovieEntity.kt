package com.example.actorsdatabaseapp.modules.sqlite

object MovieEntity {
    const val TABLE_NAME = "tableName"
    const val ID = "id"
    const val NAME = "movies"
    const val IMDBRATE = "imdbrate"

    const val CREATE_MOVIES_TABLE =
        "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT $IMDBRATE REAL)"
    const val DESTROY = "DROP TABLE IF EXISTS $TABLE_NAME"
}
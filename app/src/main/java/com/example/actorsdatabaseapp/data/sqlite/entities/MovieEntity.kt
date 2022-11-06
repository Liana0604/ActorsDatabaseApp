package com.example.actorsdatabaseapp.data.sqlite.entities

object MovieEntity {
    const val TABLE_NAME = "tableName"
    const val ID = "id"
    const val NAME = "movies"
    const val IMDBRATE = "imdbrate"
    const val ACTOR_ID = "actorId"

    const val CREATE_MOVIES_TABLE =
        "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT NOT NULL, $IMDBRATE INTEGER NOT NULL, $ACTOR_ID INTEGER NOT NULL, FOREIGN KEY ($ACTOR_ID) REFERENCES ${ActorEntity.TABLE_NAME}(${ActorEntity.ID}) ON DELETE CASCADE)"
    const val DESTROY = "DROP TABLE IF EXISTS $TABLE_NAME"
}
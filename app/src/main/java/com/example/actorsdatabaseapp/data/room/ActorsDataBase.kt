package com.example.actorsdatabaseapp.data.room

import android.content.Context
import androidx.room.*
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies
import com.example.actorsdatabaseapp.data.room.entities.PetsTypConverter

@Database(entities = [Actors::class, Movies::class], version = 1, exportSchema = false)
@TypeConverters(PetsTypConverter::class)
abstract class ActorsDataBase : RoomDatabase() {

    abstract fun actorsDao(): ActorsDao

    companion object {
        @Volatile
        private var INSTANCE: ActorsDataBase? = null

        fun getActorsDatabase(context: Context): ActorsDataBase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ActorsDataBase::class.java,
                    "actors_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
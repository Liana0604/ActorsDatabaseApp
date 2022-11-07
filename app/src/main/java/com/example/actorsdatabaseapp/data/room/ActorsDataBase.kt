package com.example.actorsdatabaseapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies

@Database(entities = [Actors::class, Movies::class], version = 1, exportSchema = false)
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